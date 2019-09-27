package com.brandonfeist.portfoliobackend.models;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.models.domain.Technology;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ProjectInputModel {
  @NotBlank(message = "Name is mandatory")
  private final String name;

  @NotBlank(message = "Project image is mandatory")
  private final String imageUrl;

  @NotBlank(message = "Summary is mandatory")
  private final String summary;

  @NotBlank(message = "Description is mandatory")
  private final String description;

  @NotBlank(message = "Project type is mandatory")
  private final String projectType;

  @Nullable
  private final Set<TechnologyInputModel> technologies;

  @NotNull
  private final Date projectDate;

  /**
   * Turns this Project Input Model into a Project object.
   *
   * @return a Project that reflects this ProjectInputModel
   */
  public Project toProject() {
    final Project project = new Project();
    final Set<Technology> convertedTechnologies = technologies
        .stream()
        .map(TechnologyInputModel::toTechnology)
        .collect(Collectors.toSet());

    project.setName(this.name);
    project.setImageUrl(this.imageUrl);
    project.setSummary(this.summary);
    project.setDescription(this.description);
    project.setProjectType(this.projectType);
    project.setTechnologies(convertedTechnologies);
    project.setProjectDate(this.projectDate);

    return project;
  }
}
