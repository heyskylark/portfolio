package com.brandonfeist.portfoliobackend.controllers;

import com.brandonfeist.portfoliobackend.models.ProjectResource;
import com.brandonfeist.portfoliobackend.models.ProjectSummaryResource;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectResourceAssembler;
import com.brandonfeist.portfoliobackend.models.assemblers.ProjectSummaryResourceAssembler;
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

    @Autowired
    public ProjectController(ProjectResourceAssembler projectResourceAssembler,
                             ProjectSummaryResourceAssembler projectSummaryResourceAssembler) {
        this.projectResourceAssembler = projectResourceAssembler;
        this.projectSummaryResourceAssembler = projectSummaryResourceAssembler;
    }

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
