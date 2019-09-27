package com.brandonfeist.portfoliobackend.services;

import com.brandonfeist.portfoliobackend.models.ProjectInputModel;
import com.brandonfeist.portfoliobackend.models.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The project service interface is responsible for interfacing all business logic regarding
 * personal projects.
 */
public interface IProjectService {

  /**
   * Returns a sorted page of Projects.
   *
   * @param pageable Pageable object is responsible for sorting, page number, and page size
   *                 of Projects.
   * @return a page of Projects, the amount and sort order are determined by the pageable.
   */
  Page<Project> getProjects(Pageable pageable);

  /**
   * Returns a Project based off the slug given. The slug must be valid (which means that a
   * project with the given slug must exist).
   *
   * @param projectSlug a unique String that represents an existing Project.
   * @return Project that is linked to the given slug identifier. Otherwise null.
   */
  Project getProject(String projectSlug);

  /**
   * Creates and saves a Project using the given Project Resource.
   *
   * @param projectInputModel client Project Input Model that will be used to initialize a Project.
   * @return the created and saved Project.
   */
  Project createProject(ProjectInputModel projectInputModel);

  /**
   * Updates an existing Project based off the Project Resource given. The slug must be valid
   * (which means that a project with the given slug must exist. Otherwise a not found will be
   * thrown). If the name of the Project has changed, the slug will be updated as well.
   *
   * @param projectInputModel client Project Input Model that will be used to update an
   *                          existing project.
   * @return the updated Project.
   */
  Project updateProject(String projectSlug, ProjectInputModel projectInputModel);

  /**
   * Deletes an existing project that is linked to the unique slug given. This slug must be valid
   * (which means that a project with the given slug must exist.
   * Otherwise a not found will be thrown).
   *
   * @param projectSlug a unique String that represents an existing Project.
   */
  void deleteProject(String projectSlug);
}
