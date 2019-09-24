package com.brandonfeist.portfoliobackend.services;

import com.brandonfeist.portfoliobackend.repositories.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class IProjectServiceTest {

  @Autowired
  private IProjectService projectService;

  @MockBean
  private ProjectRepository projectRepository;

  @Test
  public void whenGetProjectsWithDefaultPage_returnsProjectSummaryResourcePage() {

  }

  @Test
  public void whenGetProjectWithValidSlug_returnsProject() {

  }

  @Test
  public void whenGetProjectWithInvalidSlug_returnsNull() {

  }

  @Test
  public void whenCreateProjectWithValidProjectResource_returnProject() {

  }

  @Test
  public void whenCreateProjectWithInvalidProjectResource_throwBadRequest() {

  }

  @Test
  public void whenUpdateProjectWithValidProjectResource_returnProject() {

  }

  @Test
  public void whenUpdateProjectWithInvalidProjectResource_throwBadRequest() {

  }

  @Test
  public void whenDeleteProjectWithValidSlug_returnNothing() {

  }

  @Test
  public void whenDeleteProjectWithInvalidSlug_throwNotFound() {

  }
}
