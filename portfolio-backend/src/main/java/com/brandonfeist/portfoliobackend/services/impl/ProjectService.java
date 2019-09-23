package com.brandonfeist.portfoliobackend.services.impl;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import org.springframework.data.domain.Page;

public class ProjectService implements IProjectService {
  @Override
  public Page<Project> getProjects() {
    return null;
  }

  @Override
  public Project getProject(String projectSlug) {
    return null;
  }

  @Override
  public Project createProject(Project project) {
    return null;
  }

  @Override
  public Project updateProject(Project project) {
    return null;
  }

  @Override
  public void deleteProject(Project slug) {

  }
}
