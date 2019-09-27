package com.brandonfeist.portfoliobackend.repositories;

import com.brandonfeist.portfoliobackend.models.domain.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
  Technology findByName(@Param("name") String name);
}
