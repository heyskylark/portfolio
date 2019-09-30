package com.brandonfeist.portfoliobackend.models.assemblers;

import com.brandonfeist.portfoliobackend.controllers.ProjectController;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.TechnologyResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectSummaryResourceAssembler
    extends ResourceAssemblerSupport<Project, ProjectSummaryResource> {

  public ProjectSummaryResourceAssembler() {
    super(ProjectController.class, ProjectSummaryResource.class);
  }

  @Override
  public ProjectSummaryResource toResource(Project project) {
    return createResourceWithId(project.getSlug(), project);
  }

  @Override
  protected ProjectSummaryResource instantiateResource(Project project) {
    Set<TechnologyResource> technologyResource = project.getTechnologies()
        .stream()
        .map(technology -> {
          final TechnologyResource.Builder builder = new TechnologyResource.Builder();
          builder.setName(technology.getName());
          return builder.build();
        })
        .collect(Collectors.toSet());

    ProjectSummaryResource.Model.Builder model = new ProjectSummaryResource.Model.Builder()
        .setName(project.getName())
        .setImageUrl(project.getImageUrl())
        .setSummary(project.getSummary())
        .setProjectType(project.getProjectType())
        .addAllTechnologies(technologyResource)
        .setProjectDate(project.getProjectDate())
        .setSlug(project.getSlug());

    return new ProjectSummaryResource(model.build());
  }
}
