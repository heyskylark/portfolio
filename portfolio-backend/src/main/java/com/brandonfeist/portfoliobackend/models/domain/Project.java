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
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "projects")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @NotNull
  @Column(nullable = false)
  private String name;

  @NotNull
  @Column(nullable = false)
  private String summary;

  @NotNull
  @Column(nullable = false)
  private String description;

  @NotNull
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "projects_technologies",
      joinColumns = { @JoinColumn(name = "project_id") },
      inverseJoinColumns = { @JoinColumn(name = "technology_id") }
  )
  private Set<Technology> technologies;

  @NotNull
  @Column(name = "project_date", nullable = false)
  private Date projectDate;

  @NotNull
  @Column(name = "created_date", nullable = false)
  private Date createdDate;

  @NotNull
  @Column(name = "updated_date", nullable = false)
  private Date updatedDate;
}
