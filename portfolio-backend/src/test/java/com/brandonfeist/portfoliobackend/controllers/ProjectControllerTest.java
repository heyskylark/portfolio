package com.brandonfeist.portfoliobackend.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.brandonfeist.portfoliobackend.models.ProjectInputModel;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import com.brandonfeist.portfoliobackend.utils.ProjectTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

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
import org.springframework.http.MediaType;
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

  private static final String MOCK_MVC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
  private static final String TEST_SLUG = "test-slug";
  private static final String PROJECT_ENDPOINT = "/v1/projects";
  private static final MediaType MEDIA_TYPE_JSON_UTF8 =
      new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));

  private static ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProjectTestUtils projectTestUtils;

  @MockBean
  private IProjectService projectService;

  /**
   * Projects test setup. Since MockMVC formats dates differently than ObjectMapper default we must
   * manually change the format and remove the timezone difference.
   */
  @BeforeClass
  public static void init() {
    DateFormat dateFormat = new SimpleDateFormat(MOCK_MVC_DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    objectMapper = new ObjectMapper().setDateFormat(dateFormat);
  }

  @Test
  public void whenGettingProjects_returnsProjectSummaryResourcePage() throws Exception {
    final List<Project> projects = new ArrayList<>();
    projects.add(projectTestUtils.createTestProject());
    final Page<Project> pagedResponse = new PageImpl<>(projects);

    when(projectService.getProjects(any(Pageable.class))).thenReturn(pagedResponse);
    MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    verify(projectService, times(1)).getProjects(any(Pageable.class));
  }

  @Test
  public void whenProjectWithValidSlug_returnsProjectResource() throws Exception {
    final Project testProject = projectTestUtils.createTestProject();

    when(projectService.getProject(anyString())).thenReturn(testProject);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    final String expectedResponseBody = objectMapper
        .writeValueAsString(projectTestUtils.createProjectResource());
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    verify(projectService, times(1)).getProject(anyString());
    assertEquals("The project resource returned is incorrect",
        expectedResponseBody, actualResponseBody);
  }

  @Test
  public void whenProjectWithInvalidSlug_returns404() throws Exception {
    when(projectService.getProject(anyString()))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Project with slug [" + TEST_SLUG + "] was not found."));
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedBodyResponse = "Project with slug [" + TEST_SLUG + "] was not found.";
    String actualBodyResponse = mvcResult.getResponse().getErrorMessage();

    assertEquals("Invalid slug, project should not be found",
        expectedBodyResponse, actualBodyResponse);
  }

  @Test
  public void whenCreateProjectWithValidProjectResource_return201AndLocation() throws Exception {
    when(projectService.createProject(any(ProjectInputModel.class)))
        .thenReturn(projectTestUtils.createTestProject());
    this.mockMvc.perform(post(PROJECT_ENDPOINT)
        .contentType(MEDIA_TYPE_JSON_UTF8)
        .content(objectMapper.writeValueAsString(projectTestUtils.createProjectResource())))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(header().string(LOCATION, "http://localhost/v1/projects/test-project"));
  }

  @Test
  public void whenCreateProjectWithInvalidProjectResource_throwBadRequest() throws Exception {
    ProjectInputModel badProject = projectTestUtils.createTestProjectInputModel();
    badProject.setName(null);

    this.mockMvc.perform(post(PROJECT_ENDPOINT)
        .contentType(MEDIA_TYPE_JSON_UTF8)
        .content(objectMapper.writeValueAsString(badProject)))
        .andDo(print())
        .andExpect(status().isBadRequest());

    verify(projectService, never()).createProject(any(ProjectInputModel.class));
  }

  @Test
  public void whenUpdateProjectWithValidProjectResource_return204() throws Exception {
    when(projectService.updateProject(anyString(), any(ProjectInputModel.class)))
        .thenReturn(projectTestUtils.createTestProject());
    this.mockMvc.perform(put(PROJECT_ENDPOINT + "/" + TEST_SLUG)
        .contentType(MEDIA_TYPE_JSON_UTF8)
        .content(objectMapper.writeValueAsString(projectTestUtils.createProjectResource())))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void whenUpdateProjectWithInvalidSlug_return404() throws Exception {
    when(projectService.updateProject(anyString(), any(ProjectInputModel.class)))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Project with slug [" + TEST_SLUG + "] was not found."));
    final MvcResult mvcResult = this.mockMvc.perform(put(PROJECT_ENDPOINT + "/" + TEST_SLUG)
        .contentType(MEDIA_TYPE_JSON_UTF8)
        .content(objectMapper.writeValueAsString(projectTestUtils.createProjectResource())))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedBodyResponse = "Project with slug [" + TEST_SLUG + "] was not found.";
    String actualBodyResponse = mvcResult.getResponse().getErrorMessage();

    assertEquals("Invalid slug, project should not be found",
        expectedBodyResponse, actualBodyResponse);
  }

  @Test
  public void whenUpdateProjectWithInvalidProjectResource_throwBadRequest() throws Exception {
    ProjectInputModel badProject = projectTestUtils.createTestProjectInputModel();
    badProject.setName(null);

    this.mockMvc.perform(put(PROJECT_ENDPOINT + "/" + TEST_SLUG)
        .contentType(MEDIA_TYPE_JSON_UTF8)
        .content(objectMapper.writeValueAsString(badProject)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void whenDeleteProjectWithValidSlug_return204() throws Exception {
    doNothing().when(projectService).deleteProject(anyString());
    this.mockMvc.perform(delete(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void whenDeleteProjectWithInvalidSlug_return404() throws Exception {
    doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
        "Project with slug [" + TEST_SLUG + "] was not found."))
        .when(projectService).deleteProject(TEST_SLUG);
    final MvcResult mvcResult = this.mockMvc.perform(delete(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andReturn();

    String expectedBodyResponse = "Project with slug [" + TEST_SLUG + "] was not found.";
    String actualBodyResponse = mvcResult.getResponse().getErrorMessage();

    assertEquals("Invalid slug, project should not be found",
        expectedBodyResponse, actualBodyResponse);
  }
}
