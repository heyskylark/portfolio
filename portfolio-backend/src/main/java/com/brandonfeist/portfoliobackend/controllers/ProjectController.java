package com.brandonfeist.portfoliobackend.controllers;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/v1/projects")
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

  @GetMapping("/{projectSlug}")
  public ProjectResource getProject(
      @PathVariable String projectSlug
  ) {
    return null;
  }
}
