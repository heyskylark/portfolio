package com.brandonfeist.portfoliobackend.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import com.brandonfeist.portfoliobackend.utils.ProjectTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
@Import({
    ProjectResourceAssembler.class,
    ProjectSummaryResourceAssembler.class,
    ProjectTestUtils.class
})
public class ProjectControllerTest {

  private static final String TEST_SLUG = "test-slug";
  private static final String PROJECT_ENDPOINT = "/v1/projects";

  private static ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProjectTestUtils projectTestUtils;

  @MockBean
  private IProjectService projectService;

  @BeforeClass
  public static void init() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void whenGettingProjects_returnsProjectSummaryResourcePage() throws Exception {
    final List<Project> projects = new ArrayList<>();
    projects.add(projectTestUtils.createTestProject());
    final Page<Project> pagedResponse = new PageImpl<>(projects);

    when(projectService.getProjects(any(Pageable.class))).thenReturn(pagedResponse);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT)).andDo(print())
        .andExpect(status().isOk()).andReturn();

    final List<ProjectSummaryResource> projectSummaryResources = new ArrayList<>();
    projectSummaryResources.add(projectTestUtils.createProjectSummaryResource());
    final Page<ProjectSummaryResource> expectedResponseBody =
        new PageImpl<>(projectSummaryResources);
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertEquals("The project summary resources returned is incorrect",
        objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
  }

  @Test
  public void whenProjectWithValidSlug_returnsProjectResource() throws Exception {
    final Project testProject = projectTestUtils.createTestProject();

    when(projectService.getProject(anyString())).thenReturn(testProject);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print()).andExpect(status().isOk()).andReturn();


    final ProjectResource expectedResponseBody = projectTestUtils.createProjectResource();
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertEquals("The project resource returned is incorrect",
        objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
  }

  @Test
  public void whenProjectWithInvalidSlug_returns404() throws Exception {
    when(projectService.getProject(anyString()))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Project with slug [" + TEST_SLUG + "] was not found."));
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print()).andExpect(status().isNotFound()).andReturn();

    String expectedBodyResponse = "Project with slug [" + TEST_SLUG + "] was not found.";
    String actualBodyResponse = mvcResult.getResponse().getContentAsString();

    assertEquals("Invalid slug, project should not be found",
        expectedBodyResponse, actualBodyResponse);
  }

  @Test
  public void whenCreateProjectWithValidProjectResource_return201AndLocation() throws Exception {
    when(projectService.createProject(any(ProjectResource.class)))
        .thenReturn(projectTestUtils.createTestProject());
    this.mockMvc.perform(post(PROJECT_ENDPOINT)
        .content(objectMapper.writeValueAsString(projectTestUtils.createProjectResource())))
        .andDo(print()).andExpect(status().isCreated())
        .andExpect(header().string(LOCATION, "Project Resource self HATEOAS url"));
  }

  @Test
  public void whenUpdateProjectWithValidProjectResource_return204() throws Exception {
    when(projectService.updateProject(anyString(), any(ProjectResource.class)))
        .thenReturn(projectTestUtils.createTestProject());
    this.mockMvc.perform(put(PROJECT_ENDPOINT + "/" + TEST_SLUG)
        .content(objectMapper.writeValueAsString(projectTestUtils.createProjectResource())))
        .andDo(print()).andExpect(status().isNoContent());
  }

  @Test
  public void whenUpdateProjectWithInvalidSlug_return404() throws Exception {
    when(projectService.updateProject(anyString(), any(ProjectResource.class))).thenReturn(null);
    final MvcResult mvcResult = this.mockMvc.perform(put(PROJECT_ENDPOINT + "/" + TEST_SLUG)
        .content(objectMapper.writeValueAsString(projectTestUtils.createProjectResource())))
        .andDo(print()).andExpect(status().isNotFound()).andReturn();

    String expectedBodyResponse = "Project with slug [" + TEST_SLUG + "] was not found.";
    String actualBodyResponse = mvcResult.getResponse().getContentAsString();

    assertEquals("Invalid slug, project should not be found",
        expectedBodyResponse, actualBodyResponse);
  }

  @Test
  public void whenUpdateProjectWithInvalidProjectResource_throwBadRequest() throws Exception {
    Project testProject = projectTestUtils.createTestProject();
    testProject.setName(null);
    ProjectResource testProjectResource = projectTestUtils.createProjectResource(testProject);

    this.mockMvc.perform(put(PROJECT_ENDPOINT + "/" + TEST_SLUG)
        .content(objectMapper.writeValueAsString(testProjectResource)))
        .andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  public void whenDeleteProjectWithValidSlug_return204() throws Exception {
    doNothing().when(projectService).deleteProject(anyString());
    this.mockMvc.perform(delete(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print()).andExpect(status().isNoContent());
  }

  @Test
  public void whenDeleteProjectWithInvalidSlug_return404() throws Exception {
    doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Project with slug [" + TEST_SLUG + "] was not found."))
        .when(projectService).deleteProject(TEST_SLUG);
    final MvcResult mvcResult = this.mockMvc.perform(delete(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print()).andExpect(status().isNotFound()).andReturn();

    String expectedBodyResponse = "Project with slug [" + TEST_SLUG + "] was not found.";
    String actualBodyResponse = mvcResult.getResponse().getContentAsString();

    assertEquals("Invalid slug, project should not be found",
        expectedBodyResponse, actualBodyResponse);
  }
}
