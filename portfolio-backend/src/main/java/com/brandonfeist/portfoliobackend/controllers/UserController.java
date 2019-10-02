package com.brandonfeist.portfoliobackend.controllers;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/me")
public class UserController {
  @GetMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<Principal> get(final Principal principal) {
    return ResponseEntity.ok(principal);
  }
}
