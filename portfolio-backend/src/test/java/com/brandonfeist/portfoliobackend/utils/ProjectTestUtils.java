package com.brandonfeist.portfoliobackend.utils;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import java.util.Date;
import java.util.HashSet;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

/**
 * Utilities class to create Projects, ProjectResources, and ProjectSummaryResources
 * for JUnit testing.
 */
@Component
public class ProjectTestUtils {

  private static final long TEST_MILLISECONDS = 671241540000L;
  private static final long TEST_MILLISECONDS_CURRENT = 1554854340000L;

  /**
   * A utility class to create a test Project object for JUnit testing.
   *
   * @return an initialized test Project.
   */
  public Project createTestProject() {
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

  /**
   * A utility class to create a test Project Resource object for JUnit testing.
   *
   * @return an initialized test Project Resource.
   */
  public ProjectResource createProjectResource() {
    Project testProject = createTestProject();
    ProjectResource.Model.Builder model = new ProjectResource.Model.Builder()
        .setName(testProject.getName())
        .setImageUrl(testProject.getImageUrl())
        .setSummary(testProject.getSummary())
        .setDescription(testProject.getDescription())
        .addAllTechnologies(testProject.getTechnologies())
        .setProjectDate(testProject.getProjectDate());

    final Link self = new Link("http://localhost/v1/projects/test-project", "self");
    final ProjectResource returnResource = new ProjectResource(model.build());
    returnResource.add(self);

    return returnResource;
  }

  /**
   * A utility class to create a test Project Resource object from a given project
   * for JUnit testing.
   *
   * @return an initialized test Project Resource.
   */
  public ProjectResource createProjectResource(Project testProject) {
    ProjectResource.Model.Builder model = new ProjectResource.Model.Builder()
        .setName(testProject.getName())
        .setImageUrl(testProject.getImageUrl())
        .setSummary(testProject.getSummary())
        .setDescription(testProject.getDescription())
        .addAllTechnologies(testProject.getTechnologies())
        .setProjectDate(testProject.getProjectDate());

    return new ProjectResource(model.build());
  }

  /**
   * A utility class to create a test Project Summary Resource object for JUnit testing.
   *
   * @return an initialized test Project Summary Resource.
   */
  public ProjectSummaryResource createProjectSummaryResource() {
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
