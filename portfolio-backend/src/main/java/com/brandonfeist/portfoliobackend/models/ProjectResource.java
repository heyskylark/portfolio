package com.brandonfeist.portfoliobackend.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.inferred.freebuilder.FreeBuilder;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;
import java.util.List;

public class ProjectResource extends ResourceSupport {

    @FreeBuilder
    interface Model {
        String getName();

        String getImageUrl();

        String getDescription();

        List<String> getTechnologies();

        Date getProjectDate();

        class Builder extends ProjectResource_Model_Builder {
        }
    }

    @JsonUnwrapped
    private final Model model;

    ProjectResource(Model model) {
        this.model = model;
    }
}
