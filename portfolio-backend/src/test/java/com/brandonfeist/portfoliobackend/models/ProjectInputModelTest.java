package com.brandonfeist.portfoliobackend.models;

import static org.junit.Assert.assertEquals;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.utils.ProjectTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(ProjectTestUtils.class)
public class ProjectInputModelTest {

  private ProjectInputModel projectInputModel;

  @Autowired
  private ProjectTestUtils projectTestUtils;

  @Before
  public void init() {
    projectInputModel = projectTestUtils.createTestProjectInputModel("Test Project");
  }

  @Test
  public void whenConvertingProjectInputModelToProject_allValuesMatch() {
    final Project testProject = projectInputModel.toProject();

    assertEquals("Project name does not match with input model",
        projectInputModel.getName(), testProject.getName());
    assertEquals("Project image url does not match with input model",
        projectInputModel.getImageUrl(), testProject.getImageUrl());
    assertEquals("Project summary does not match with input model",
        projectInputModel.getSummary(), testProject.getSummary());
    assertEquals("Project description does not match with input model",
        projectInputModel.getDescription(), testProject.getDescription());
    assertEquals("Project project types does not match with input model",
        projectInputModel.getProjectType(), testProject.getProjectType());
    assertEquals("Project technologies types does not match with input model",
        projectTestUtils.createTechnologies(), testProject.getTechnologies());
    assertEquals("Project project date types does not match with input model",
        projectInputModel.getProjectDate(), testProject.getProjectDate());
  }
}
