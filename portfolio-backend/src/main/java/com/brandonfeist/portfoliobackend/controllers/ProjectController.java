package com.brandonfeist.portfoliobackend.controllers;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/projects")
public class ProjectController {

  private final ProjectResourceAssembler projectResourceAssembler;
  private final ProjectSummaryResourceAssembler projectSummaryResourceAssembler;
  private final IProjectService projectService;

  /**
   * Project Controller constructor used to create the project controller object that will route
   * all project based API calls and convert Project domain objects to client friendly
   * Project Resources.
   *
   * @param projectResourceAssembler an assembler used to create a full detailed project resource.
   * @param projectSummaryResourceAssembler an assembler used to create a project resource summary.
   * @param projectService the service that is responsible for all project based business logic.
   */
  @Autowired
  public ProjectController(ProjectResourceAssembler projectResourceAssembler,
                           ProjectSummaryResourceAssembler projectSummaryResourceAssembler,
                           IProjectService projectService) {
    this.projectResourceAssembler = projectResourceAssembler;
    this.projectSummaryResourceAssembler = projectSummaryResourceAssembler;
    this.projectService = projectService;
  }

  @GetMapping
  public PagedResources<ProjectSummaryResource> getProjects(
      PagedResourcesAssembler<ProjectSummaryResource> projectSummaryResourceAssembler,
      @SortDefault("project_date") Pageable pageable
  ) {
    return null;
  }

  /**
   * Projects controller method that returns a Project related to the unique slug given.
   * If a project does not exist with the given slug, getProject will return a 404.
   * Projects are converted to ProjectResource for client consumption.
   *
   * @param projectSlug a unique identifier tied to a Project.
   * @return if a project exists with the given slug that will be returned, otherwise a 404.
   */
  @GetMapping("/{projectSlug}")
  public ProjectResource getProject(
      @PathVariable String projectSlug
  ) {
    Project project = projectService.getProject(projectSlug);

    return projectResourceAssembler.toResource(project);
  }
}
