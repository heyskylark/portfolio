package com.brandonfeist.portfoliobackend.models;

import com.brandonfeist.portfoliobackend.models.domain.Technology;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.Date;
import java.util.List;
import org.inferred.freebuilder.FreeBuilder;
import org.springframework.hateoas.ResourceSupport;

public class ProjectSummaryResource extends ResourceSupport {

  @FreeBuilder
  public interface Model {
    String getName();

    String getImageUrl();

    String getSummary();

    String getProjectType();

    List<TechnologyResource> getTechnologies();

    Date getProjectDate();

    String getSlug();

    class Builder extends ProjectSummaryResource_Model_Builder {
    }
  }

  @JsonUnwrapped
  private final Model model;

  public ProjectSummaryResource(Model model) {
    this.model = model;
  }
}
