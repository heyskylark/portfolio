package com.brandonfeist.portfoliobackend.models.assemblers;

import com.brandonfeist.portfoliobackend.controllers.ProjectController;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.domain.Project;
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
        return null;
    }
}
