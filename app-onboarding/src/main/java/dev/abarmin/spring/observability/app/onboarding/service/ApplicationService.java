package dev.abarmin.spring.observability.app.onboarding.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.observability.app.onboarding.event.publisher.ApplicationEventPublisher;
import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import dev.abarmin.spring.observability.app.onboarding.model.IdentifyVerification;
import dev.abarmin.spring.observability.app.onboarding.model.VerificationStatus;
import dev.abarmin.spring.observability.app.onboarding.repository.ApplicantRepository;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Timer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private Timer verificationTimer;

  @Timed("applicants.save-time")
  public Applicant save(final Applicant applicant) {
    if (applicant.getIdentifyVerification() == null) {
      final IdentifyVerification verification = new IdentifyVerification();
      verification.setRequestSent(LocalDateTime.now());
      applicant.setIdentifyVerification(verification);
    }
    final Applicant savedApplicant = repository.save(applicant);
    eventPublisher.applicantCreated(savedApplicant);
    return savedApplicant;
  }

  public Applicant updateVerification(final Applicant applicant,
                                      final VerificationStatus status) {

    final IdentifyVerification verification =
        Optional.ofNullable(applicant.getIdentifyVerification())
            .orElse(new IdentifyVerification());
    verification.setResponseReceived(LocalDateTime.now());
    verification.setStatus(status);
    applicant.setIdentifyVerification(verification);

    verificationTimer.record(
        Duration.between(
            verification.getRequestSent(),
            verification.getResponseReceived()
        )
    );

    return repository.save(applicant);
  }

  public Optional<Applicant> findOne(final Long applicantId) {
    return repository.findById(applicantId);
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
