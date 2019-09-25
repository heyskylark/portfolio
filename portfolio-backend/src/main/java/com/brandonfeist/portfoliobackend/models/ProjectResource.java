package com.brandonfeist.portfoliobackend.models;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import com.brandonfeist.portfoliobackend.models.domain.Technology;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.Date;
import java.util.Set;

import org.inferred.freebuilder.FreeBuilder;
import org.springframework.hateoas.ResourceSupport;



public class ProjectResource extends ResourceSupport {

  @FreeBuilder
  public interface Model {
    String getName();

    String getImageUrl();

    String getSummary();

    String getDescription();

    Set<Technology> getTechnologies();

    Date getProjectDate();

    class Builder extends ProjectResource_Model_Builder {
    }
  }

  @JsonUnwrapped
  private final Model model;

  public ProjectResource(Model model) {
    this.model = model;
  }
}
