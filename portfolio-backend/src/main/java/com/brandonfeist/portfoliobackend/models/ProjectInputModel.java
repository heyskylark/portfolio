package com.brandonfeist.portfoliobackend.models;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.models.domain.Technology;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectInputModel {
  @NotBlank(message = "Project image is mandatory")
  private String imageUrl;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Summary is mandatory")
  private String summary;

  @NotBlank(message = "Description is mandatory")
  private String description;

  private Set<Technology> technologies;

  @NotNull
  private Date projectDate;

  /**
   * Turns this Project Input Model into a Project object.
   *
   * @return a Project that reflects this ProjectInputModel
   */
  public Project toProject() {
    final Project project = new Project();

    project.setName(this.name);
    project.setImageUrl(this.imageUrl);
    project.setDescription(this.summary);
    project.setDescription(this.description);
    project.setTechnologies(this.technologies);
    project.setProjectDate(this.projectDate);

    return project;
  }
}
