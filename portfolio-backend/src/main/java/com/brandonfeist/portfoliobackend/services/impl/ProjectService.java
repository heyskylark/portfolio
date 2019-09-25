package com.brandonfeist.portfoliobackend.services.impl;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.repositories.ProjectRepository;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProjectService implements IProjectService {

  private final ProjectRepository projectRepository;

  @Autowired
  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public Page<Project> getProjects(Pageable pageable) {
    return null;
  }

  @Override
  public Project getProject(String projectSlug) {
    return null;
  }

  @Override
  public Project createProject(ProjectResource projectResource) {
    return null;
  }

  @Override
  public Project updateProject(String projectSlug, ProjectResource projectResource) {
    return null;
  }

  @Override
  public void deleteProject(String projectSlug) {

  }
}
