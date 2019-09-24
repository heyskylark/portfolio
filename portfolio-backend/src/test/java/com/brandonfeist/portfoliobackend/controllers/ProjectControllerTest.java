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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
@Import({ ProjectResourceAssembler.class, ProjectSummaryResourceAssembler.class })
public class ProjectControllerTest {

  private static final String TEST_SLUG = "test-slug";
  private static final String PROJECT_ENDPOINT = "/v1/projects";
  private static final long TEST_MILLISECONDS = 671241540000L;
  private static final long TEST_MILLISECONDS_CURRENT = 1554854340000L;

  private static ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private IProjectService projectService;

  @BeforeClass
  public static void init() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void whenGettingProjects_returnsProjectSummaryResourcePage() throws Exception {
    final List<Project> projects = new ArrayList<>();
    projects.add(createTestProject());
    final Page<Project> pagedResponse = new PageImpl<>(projects);

    when(projectService.getProjects()).thenReturn(pagedResponse);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT)).andDo(print())
        .andExpect(status().isOk()).andReturn();

    final List<ProjectSummaryResource> projectSummaryResources = new ArrayList<>();
    projectSummaryResources.add(createProjectSummaryResource());
    final Page<ProjectSummaryResource> expectedResponseBody =
        new PageImpl<>(projectSummaryResources);
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertEquals("The project summary resources returned is incorrect",
        objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
  }

  @Test
  public void whenProjectWithValidSlug_returnsProjectResource() throws Exception {
    final Project testProject = createTestProject();

    when(projectService.getProject(anyString())).thenReturn(testProject);
    final MvcResult mvcResult = this.mockMvc.perform(get(PROJECT_ENDPOINT + "/" + TEST_SLUG))
        .andDo(print()).andExpect(status().isOk()).andReturn();


    final ProjectResource expectedResponseBody = createProjectResource();
    final String actualResponseBody = mvcResult.getResponse().getContentAsString();

    assertEquals("The project resource returned is incorrect",
        objectMapper.writeValueAsString(expectedResponseBody), actualResponseBody);
  }

  private Project createTestProject() {
    Project testProject = new Project();
    testProject.setName("Test Project");
    testProject.setImageUrl("https://www.test.com/testProjectImg");
    testProject.setSummary("This is a test summary.");
    testProject.setDescription("This is a test description, it is a bit longer...");
    testProject.setTechnologies(new HashSet<>());
    testProject.setSlug("test-project");
    testProject.setProjectDate(new Date(TEST_MILLISECONDS));
    testProject.setCreatedDate(new Date(TEST_MILLISECONDS_CURRENT));
    testProject.setUpdatedDate(new Date(TEST_MILLISECONDS_CURRENT));

    return testProject;
  }

  private ProjectResource createProjectResource() {
    Project testProject = createTestProject();
    ProjectResource.Model.Builder model = new ProjectResource.Model.Builder()
        .setName(testProject.getName())
        .setImageUrl(testProject.getImageUrl())
        .setDescription(testProject.getDescription())
        .addAllTechnologies(testProject.getTechnologies())
        .setProjectDate(testProject.getProjectDate());

    return new ProjectResource(model.build());
  }

  private ProjectSummaryResource createProjectSummaryResource() {
    Project testProject = createTestProject();
    ProjectSummaryResource.Model.Builder model = new ProjectSummaryResource.Model.Builder()
        .setName(testProject.getName())
        .setImageUrl(testProject.getImageUrl())
        .setSummary(testProject.getSummary())
        .addAllTechnologies(testProject.getTechnologies())
        .setProjectDate(testProject.getProjectDate());

    return new ProjectSummaryResource(model.build());
  }
}
