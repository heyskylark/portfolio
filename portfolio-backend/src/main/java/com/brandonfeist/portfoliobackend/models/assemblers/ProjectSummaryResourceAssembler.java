package com.brandonfeist.portfoliobackend.models.assemblers;

import com.brandonfeist.portfoliobackend.controllers.ProjectController;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import org.springframework.hateoas.mvc.IdentifiableResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectSummaryResourceAssembler
    extends IdentifiableResourceAssemblerSupport<Project, ProjectSummaryResource> {

  public ProjectSummaryResourceAssembler() {
    super(ProjectController.class, ProjectSummaryResource.class);
  }

  @Override
  public ProjectSummaryResource toResource(Project project) {
    return createResourceWithId(project.getSlug(), project);
  }

  @Override
  protected ProjectSummaryResource instantiateResource(Project project) {
    ProjectSummaryResource.Model.Builder model = new ProjectSummaryResource.Model.Builder()
        .setName(project.getName())
        .setImageUrl(project.getImageUrl())
        .setSummary(project.getSummary())
        .addAllTechnologies(project.getTechnologies())
        .setProjectDate(project.getProjectDate());

    return new ProjectSummaryResource(model.build());
  }
}
