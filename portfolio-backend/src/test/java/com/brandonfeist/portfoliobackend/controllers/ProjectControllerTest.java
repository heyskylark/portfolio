package com.brandonfeist.portfoliobackend.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import com.brandonfeist.portfoliobackend.utils.ProjectUtils;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
@Import({
    ProjectResourceAssembler.class,
    ProjectSummaryResourceAssembler.class,
    ProjectUtils.class
})
public class ProjectControllerTest {

  private static final String TEST_SLUG = "test-slug";
  private static final String PROJECT_ENDPOINT = "/v1/projects";

  private static ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProjectUtils projectUtils;

  @MockBean
  private IProjectService projectService;

  @BeforeClass
  public static void init() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void whenGettingProjects_returnsProjectSummaryResourcePage() throws Exception {
    final List<Project> projects = new ArrayList<>();
    projects.add(projectUtils.createTestProject());
    final Page<Project> pagedResponse = new PageImpl<>(projects);

    when(projectService.getProjects()).thenReturn(pagedResponse);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT)).andDo(print())
        .andExpect(status().isOk()).andReturn();

    final List<ProjectSummaryResource> projectSummaryResources = new ArrayList<>();
    projectSummaryResources.add(projectUtils.createProjectSummaryResource());
    final Page<ProjectSummaryResource> expectedResponseBody =
        new PageImpl<>(projectSummaryResources);
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertEquals("The project summary resources returned is incorrect",
        objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
  }

  @Test
  public void whenProjectWithValidSlug_returnsProjectResource() throws Exception {
    final Project testProject = projectUtils.createTestProject();

    when(projectService.getProject(anyString())).thenReturn(testProject);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print()).andExpect(status().isOk()).andReturn();


    final ProjectResource expectedResponseBody = projectUtils.createProjectResource();
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertEquals("The project resource returned is incorrect",
        objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
  }
}
