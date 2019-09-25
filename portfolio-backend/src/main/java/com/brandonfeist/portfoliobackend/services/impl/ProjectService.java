package com.brandonfeist.portfoliobackend.services.impl;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.repositories.ProjectRepository;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import com.github.slugify.Slugify;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

  private final Slugify slugify;

  @Autowired
  public ProjectService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
    this.slugify = new Slugify();
  }

  @Override
  public Page<Project> getProjects(Pageable pageable) {
    return projectRepository.findAll(pageable);
  }

  @Override
  public Project getProject(String projectSlug) {
    final Project project = projectRepository.findBySlug(projectSlug);

    if (project == null) {
      log.info("Project with slug [{}] was not found.", projectSlug);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Project with slug [" + projectSlug + "] was not found.");
    }

    return project;
  }

  @Override
  public Project createProject(ProjectResource projectResource) {
    final Project project = projectResource.toProject();
    project.setSlug(generateSlug(project.getName()));

    return projectRepository.save(project);
  }

  @Override
  public Project updateProject(String projectSlug, ProjectResource projectResource) {
    final Project updatedProject = projectResource.toProject();
    final Project oldProject = projectRepository.findBySlug(projectSlug);

    if (oldProject == null) {
      log.info("Project with slug [{}] was not found.", projectSlug);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Project with slug [" + projectSlug + "] was not found.");
    }

    updatedProject.setId(oldProject.getId());
    updatedProject.setCreatedDate(oldProject.getCreatedDate());
    if (updatedProject.getName().equals(oldProject.getName())) {
      updatedProject.setSlug(oldProject.getSlug());
    } else {
      updatedProject.setSlug(generateSlug(updatedProject.getName()));
    }

    return projectRepository.save(updatedProject);
  }

  @Override
  public void deleteProject(String projectSlug) {
    final Project project = projectRepository.findBySlug(projectSlug);

    if (project == null) {
      log.info("Project with slug [{}] was not found.", projectSlug);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "Project with slug [" + projectSlug + "] was not found.");
    }

    projectRepository.delete(project);
  }

  private String generateSlug(String seed) {
    final String originalSlug = slugify.slugify(seed);
    final List<Project> existingProjectsList = projectRepository
        .findBySlugStartingWith(originalSlug);
    final Map<String, Project> existingProjectsMap = existingProjectsList.stream()
        .collect(Collectors.toMap(Project::getSlug, Function.identity()));

    int index = 1;
    String returnSlug = originalSlug;
    while (existingProjectsMap.containsKey(returnSlug)) {
      returnSlug = originalSlug + "-" + index++;
    }

    return returnSlug;
  }
}
