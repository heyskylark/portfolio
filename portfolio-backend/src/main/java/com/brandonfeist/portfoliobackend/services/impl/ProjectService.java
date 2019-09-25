package com.brandonfeist.portfoliobackend.services.impl;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.repositories.ProjectRepository;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    Project project = projectRepository.findBySlug(projectSlug);

    if (project == null) {
      log.info("Project with slug [{}] was not found.", projectSlug);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Project with slug [" + projectSlug + "] was not found.");
    }

    return projectRepository.findBySlug(projectSlug);
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
