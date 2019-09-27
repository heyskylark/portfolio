package com.brandonfeist.portfoliobackend.models.assemblers;

import com.brandonfeist.portfoliobackend.controllers.ProjectController;
import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectResourceAssembler
    extends ResourceAssemblerSupport<Project, ProjectResource> {

  public ProjectResourceAssembler() {
    super(ProjectController.class, ProjectResource.class);
  }

  @Override
  public ProjectResource toResource(Project project) {
    return createResourceWithId(project.getSlug(), project);
  }

  @Override
  protected ProjectResource instantiateResource(Project project) {
    final ProjectResource.Model.Builder model = new ProjectResource.Model.Builder()
        .setName(project.getName())
        .setImageUrl(project.getImageUrl())
        .setSummary(project.getSummary())
        .setDescription(project.getDescription())
        .setProjectType(project.getProjectType())
        .addAllTechnologies(project.getTechnologies())
        .setProjectDate(project.getProjectDate());

    return new ProjectResource(model.build());
  }
}
