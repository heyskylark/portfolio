package com.brandonfeist.portfoliobackend.models;

import com.brandonfeist.portfoliobackend.models.domain.Technology;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TechnologyInputModel {

  public TechnologyInputModel() {
  }

  public TechnologyInputModel(String name) {
    this.name = name;
  }

  @NotBlank
  private String name;

  Technology toTechnology() {
    Technology technology = new Technology();

    technology.setName(this.name);

    return technology;
  }
}
