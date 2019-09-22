package com.brandonfeist.portfoliobackend.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.inferred.freebuilder.FreeBuilder;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;
import java.util.List;

public class ProjectSummaryResource extends ResourceSupport {

    @FreeBuilder
    public interface Model {
        String getName();

        String getImageUrl();

        String getSummary();

        List<String> getTechnologies();

        Date getProjectDate();

        class Builder extends ProjectSummaryResource_Model_Builder {
        }
    }

    @JsonUnwrapped
    private final Model model;

    public ProjectSummaryResource(Model model) {
        this.model = model;
    }
}
