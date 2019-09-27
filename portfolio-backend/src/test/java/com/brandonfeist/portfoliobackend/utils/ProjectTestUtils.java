package com.brandonfeist.portfoliobackend.utils;

import com.brandonfeist.portfoliobackend.models.ProjectInputModel;
import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.TechnologyInputModel;
import com.brandonfeist.portfoliobackend.models.TechnologyResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.models.domain.Technology;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    testProject.setProjectType("Personal");
    testProject.setTechnologies(createTechnologies());
    testProject.setSlug("test-project");
    testProject.setProjectDate(new Date(TEST_MILLISECONDS));
    testProject.setCreatedDate(new Date(TEST_MILLISECONDS_CURRENT));
    testProject.setUpdatedDate(new Date(TEST_MILLISECONDS_CURRENT));

    return testProject;
  }

  /**
   * A utility class to create a test Project Input Model for JUnit testing.
   *
   * @return an initialized Project Input Model.
   */
  public ProjectInputModel createTestProjectInputModel(String name) {
    return new ProjectInputModel(
        name,
        "https://www.test.com/testProjectImg",
        "This is a test summary.",
        "This is a test description, it is a bit longer...",
        "Personal",
        createTechnologyInputModel(),
        new Date(TEST_MILLISECONDS)
    );
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
        .setProjectType(testProject.getProjectType())
        .addAllTechnologies(createTechnologyResources())
        .setProjectDate(testProject.getProjectDate());

    final Link self = new Link("http://localhost/v1/projects/test-project", "self");
    final ProjectResource returnResource = new ProjectResource(model.build());
    returnResource.add(self);

    return returnResource;
  }

  /**
   * Creates a single technology object.
   *
   * @return a technology object with the name React.
   */
  public Technology createTechnology() {
    final Technology technology = new Technology();
    technology.setName("React");

    return technology;
  }

  /**
   * Creates a set of Technology objects. This set only contains one technology object which is
   * made using the {@link #createTechnology()}  createTechnology} method.
   *
   * @return a set of Technologies.
   */
  public Set<Technology> createTechnologies() {
    final Set<Technology> technologies = new HashSet<>();
    final Technology technology = createTechnology();
    technologies.add(technology);

    return technologies;
  }

  private Set<TechnologyResource> createTechnologyResources() {
    final Set<TechnologyResource> technologyResources = new HashSet<>();
    final TechnologyResource.Builder technologyResource = new TechnologyResource.Builder();
    technologyResources.add(technologyResource.setName("React").build());

    return technologyResources;
  }

  private Set<TechnologyInputModel> createTechnologyInputModel() {
    final Set<TechnologyInputModel> technologyInputModels = new HashSet<>();
    technologyInputModels.add(new TechnologyInputModel("React"));

    return technologyInputModels;
  }
}
