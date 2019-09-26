package com.brandonfeist.portfoliobackend.controllers;

import com.brandonfeist.portfoliobackend.models.ProjectInputModel;
import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.services.IProjectService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  /**
   * Returns a page of Project Summary Resources in the order specified by the pageable object.
   * Projects are converted to a Project Summary Resource before being handed off to the client.
   *
   * @param resourcesAssembler an assembler to convert Projects to Project Summary Resources.
   * @param pageable specifies the sort order, size of page, and page number.
   * @return a page resource of Project Summary Resources.
   */
  @GetMapping
  public PagedResources<ProjectSummaryResource> getProjects(
      PagedResourcesAssembler<Project> resourcesAssembler,
      @SortDefault("project_date") Pageable pageable
  ) {
    Page<Project> projects = projectService.getProjects(pageable);

    return resourcesAssembler.toResource(projects, projectSummaryResourceAssembler);
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

  /**
   * This is used to create a Project using a Project Input Model given by the user.
   *
   * @param projectInputModel a user input model that will be used to create a Project.
   * @return Http code 201 and a Location header pointing to the created project.
   */
  @PostMapping
  public ResponseEntity<Void> createProject(
      @Valid @RequestBody ProjectInputModel projectInputModel
  ) {
    ProjectResource createdProject = projectResourceAssembler
        .toResource(projectService.createProject(projectInputModel));

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(createdProject.getLink("self").getTemplate().expand());

    return new ResponseEntity<>(headers, HttpStatus.CREATED);
  }

  @PutMapping("/{projectSlug}")
  public ResponseEntity<Void> updateProject(
      @PathVariable String projectSlug,
      @Valid @RequestBody ProjectInputModel projectInputModel
  ) {
    projectService.updateProject(projectSlug, projectInputModel);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{projectSlug}")
  public ResponseEntity<Void> deleteProject(
      @PathVariable String projectSlug
  ) {
    projectService.deleteProject(projectSlug);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
