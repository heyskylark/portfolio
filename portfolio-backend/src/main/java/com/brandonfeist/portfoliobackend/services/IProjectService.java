package com.brandonfeist.portfoliobackend.services;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import org.springframework.data.domain.Page;

public interface IProjectService {
    Page<Project> getProjects();

    Project getProject(String projectSlug);

    Project createProject(Project project);

    Project updateProject(Project project);

    void deleteProject(Project slug);
}
