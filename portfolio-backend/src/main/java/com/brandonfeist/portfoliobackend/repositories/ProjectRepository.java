package com.brandonfeist.portfoliobackend.repositories;

import com.brandonfeist.portfoliobackend.models.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
  Project findBySlug(@Param("slug") String slug);
}
