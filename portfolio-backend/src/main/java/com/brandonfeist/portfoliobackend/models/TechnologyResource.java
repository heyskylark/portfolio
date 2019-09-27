package com.brandonfeist.portfoliobackend.models;

import org.inferred.freebuilder.FreeBuilder;

@FreeBuilder
public interface TechnologyResource {

  String getName();

  class Builder extends TechnologyResource_Builder {
  }
}
