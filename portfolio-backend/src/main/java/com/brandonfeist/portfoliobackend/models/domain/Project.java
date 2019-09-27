package com.brandonfeist.portfoliobackend.models.domain;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Project image is mandatory")
  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @NotBlank(message = "Name is mandatory")
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Summary is mandatory")
  @Column(nullable = false)
  private String summary;

  @NotBlank(message = "Description is mandatory")
  @Column(nullable = false)
  private String description;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "projects_technologies",
      joinColumns = { @JoinColumn(name = "project_id") },
      inverseJoinColumns = { @JoinColumn(name = "technology_id") }
  )
  private Set<Technology> technologies;

  @NotBlank(message = "Project type is mandatory")
  @Column(name = "project_type", nullable = false)
  private String projectType;

  @NotBlank(message = "Slug is mandatory")
  @Column(nullable = false, unique = true)
  private String slug;

  @NotNull
  @Column(name = "project_date", nullable = false)
  private Date projectDate;

  @NotNull
  @PastOrPresent(message = "Created date cannot be in the future")
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  @NotNull
  @PastOrPresent(message = "Updated date cannot be in the future")
  @Column(name = "updated_date", nullable = false)
  private Date updatedDate;
}
