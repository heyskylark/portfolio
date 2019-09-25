package com.brandonfeist.portfoliobackend.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.repositories.ProjectRepository;
import com.brandonfeist.portfoliobackend.services.impl.ProjectService;
import com.brandonfeist.portfoliobackend.utils.ProjectTestUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

@RunWith(SpringRunner.class)
@Import(ProjectTestUtils.class)
public class ProjectServiceTest {

  private static final String TEST_SLUG = "test-slug";

  private Project testProject;

  @InjectMocks
  private ProjectService projectService;

  @Autowired
  private ProjectTestUtils projectTestUtils;

  @Mock
  private ProjectRepository projectRepository;

  @Before
  public void init() {
    testProject = projectTestUtils.createTestProject();
  }

  @Test
  public void whenGetProjectsWithDefaultPage_returnsProjectSummaryResourcePage() {
    final Pageable pageable = PageRequest.of(0, 1);
    final List<Project> projects = new ArrayList<>();
    projects.add(testProject);
    final Page<Project> projectPage = new PageImpl<>(projects);
    when(projectRepository.findAll(any(Pageable.class))).thenReturn(projectPage);
    projectService.getProjects(pageable);

    verify(projectRepository, times(1)).findAll(any(Pageable.class));
  }

  @Test
  public void whenGetProjectWithValidSlug_returnsProject() {
    when(projectRepository.findBySlug(anyString()))
        .thenReturn(testProject);
    projectService.getProject(TEST_SLUG);

    verify(projectRepository, times(1)).findBySlug(anyString());
  }

  @Test(expected = ResponseStatusException.class)
  public void whenGetProjectWithInvalidSlug_throws404() {
    when(projectRepository.findBySlug(anyString())).thenReturn(null);
    projectService.getProject(TEST_SLUG);

    verify(projectRepository, times(1)).findBySlug(anyString());
  }

  @Test
  public void whenCreateProjectWithValidProjectResource_returnProject() {
    when(projectRepository.findBySlugStartingWith(anyString())).thenReturn(Collections.emptyList());
    when(projectRepository.save(any(Project.class))).thenReturn(null);
    projectService.createProject(projectTestUtils.createProjectResource());

    verify(projectRepository, times(1)).findBySlugStartingWith(anyString());
    verify(projectRepository, times(1)).save(any(Project.class));
  }

  @Test
  public void whenUpdateProjectWithValidProjectResource_returnProject() {
    when(projectRepository.findBySlug(anyString())).thenReturn(testProject);
    when(projectRepository.save(any(Project.class))).thenReturn(null);
    projectService.updateProject(TEST_SLUG, projectTestUtils.createProjectResource(testProject));

    verify(projectRepository, times(1)).findBySlug(anyString());
    verify(projectRepository, never()).findBySlugStartingWith(anyString());
    verify(projectRepository, times(1)).save(any(Project.class));
  }

  @Test
  public void whenUpdateProjectWithNameChange_returnProjectWithNewSlug() {
    when(projectRepository.findBySlug(anyString())).thenReturn(testProject);
    when(projectRepository.findBySlugStartingWith(anyString())).thenReturn(Collections.emptyList());
    when(projectRepository.save(any(Project.class))).thenReturn(null);
    Project updatedProject = projectTestUtils.createTestProject();
    updatedProject.setName("Test123");
    projectService.updateProject(TEST_SLUG, projectTestUtils.createProjectResource(updatedProject));

    verify(projectRepository, times(1)).findBySlug(anyString());
    verify(projectRepository, times(1)).findBySlugStartingWith(anyString());
    verify(projectRepository, times(1)).save(any(Project.class));
  }

  @Test(expected = ResponseStatusException.class)
  public void whenUpdateProjectWithInvalidSlug_throws404() {
    when(projectRepository.findBySlug(anyString())).thenReturn(null);
    projectService.updateProject(TEST_SLUG, projectTestUtils.createProjectResource(testProject));

    verify(projectRepository, times(1)).findBySlug(anyString());
    verify(projectRepository, never()).findBySlugStartingWith(anyString());
    verify(projectRepository, never()).save(any(Project.class));
  }

  @Test
  public void whenDeleteProjectWithValidSlug_returnNothing() {
    when(projectRepository.findBySlug(anyString())).thenReturn(testProject);
    doNothing().when(projectRepository).delete(any(Project.class));
    projectService.deleteProject(TEST_SLUG);

    verify(projectRepository, times(1)).findBySlug(anyString());
    verify(projectRepository, times(1)).delete(any(Project.class));
  }

  @Test(expected = ResponseStatusException.class)
  public void whenDeleteProjectWithInvalidSlug_throwNotFound() {
    when(projectRepository.findBySlug(anyString())).thenReturn(null);
    projectService.deleteProject(TEST_SLUG);

    verify(projectRepository, times(1)).findBySlug(anyString());
    verify(projectRepository, never()).delete(any(Project.class));
  }
}
