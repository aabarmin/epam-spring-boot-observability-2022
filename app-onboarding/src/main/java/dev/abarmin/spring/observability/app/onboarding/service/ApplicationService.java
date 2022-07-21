package dev.abarmin.spring.observability.app.onboarding.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.observability.app.onboarding.event.publisher.ApplicationEventPublisher;
import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import dev.abarmin.spring.observability.app.onboarding.repository.ApplicantRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
@RequiredArgsConstructor
public class ApplicationService {
  private final ApplicantRepository repository;
  private final ObjectMapper objectMapper;
  private final ApplicationEventPublisher eventPublisher;

  public Applicant create(final Applicant applicant) {
    final Applicant savedApplicant = repository.save(applicant);
    eventPublisher.applicantCreated(savedApplicant);
    return savedApplicant;
  }

  @SneakyThrows
  public Applicant update(final Long applicantId,
                          final JsonNode update) {

    final Applicant applicant = repository.findById(applicantId)
        .orElseThrow(() -> new RuntimeException(String.format(
            "Can't get applicant with id %s",
            applicantId
        )));

    final Applicant updatedApplicant = objectMapper
        .readerForUpdating(applicant)
        .<Applicant>readValue(update);

    final Applicant savedApplicant = repository.save(updatedApplicant);
    eventPublisher.applicantUpdated(savedApplicant);
    return savedApplicant;
  }
}
