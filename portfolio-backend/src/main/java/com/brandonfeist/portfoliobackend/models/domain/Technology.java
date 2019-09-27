package com.brandonfeist.portfoliobackend.models.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
@Entity
@Table(name = "technologies")
public class Technology {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @NaturalId
  @Column(nullable = false, unique = true)
  private String name;
}
