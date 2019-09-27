package com.brandonfeist.portfoliobackend.models.assemblers;

import com.brandonfeist.portfoliobackend.controllers.ProjectController;
import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.TechnologyResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import java.util.Set;
import java.util.stream.Collectors;
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
    Set<TechnologyResource> technologyResource = project.getTechnologies()
        .stream()
        .map(technology -> {
          final TechnologyResource.Builder builder = new TechnologyResource.Builder();
          builder.setName(technology.getName());
          return builder.build();
        })
        .collect(Collectors.toSet());

    final ProjectResource.Model.Builder model = new ProjectResource.Model.Builder()
        .setName(project.getName())
        .setImageUrl(project.getImageUrl())
        .setSummary(project.getSummary())
        .setDescription(project.getDescription())
        .setProjectType(project.getProjectType())
        .addAllTechnologies(technologyResource)
        .setProjectDate(project.getProjectDate());

    return new ProjectResource(model.build());
  }
}
