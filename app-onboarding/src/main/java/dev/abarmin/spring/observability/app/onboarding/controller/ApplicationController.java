package dev.abarmin.spring.observability.app.onboarding.controller;

import com.fasterxml.jackson.databind.JsonNode;
import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import dev.abarmin.spring.observability.app.onboarding.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationController {
  private final ApplicationService applicationService;

  @PostMapping("/applicants")
  @ResponseStatus(HttpStatus.CREATED)
  public Applicant createApplication(final @RequestBody Applicant applicant) {
    log.info("Request to create applicant {}", applicant);

    return applicationService.save(applicant);
  }

  @GetMapping("/applicants/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Applicant viewApplicant(final @PathVariable("id") Long id) {
    log.info("Reading applicant with id {}", id);

    return applicationService.findOne(id)
        .orElseThrow(() -> new RuntimeException(String.format(
            "No applicant with id %s",
            id
        )));
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/applicants/{id}")
  public Applicant updateApplicant(final @PathVariable("id") Long id,
                                   final @RequestBody JsonNode update) {

    log.info("Updating applicant {} with body {}", id, update);

    return applicationService.update(id, update);
  }
}
