package dev.abarmin.spring.observability.app.onboarding.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.observability.app.onboarding.event.publisher.VerificationEventPublisher;
import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import dev.abarmin.spring.observability.app.onboarding.model.VerificationStatus;
import dev.abarmin.spring.observability.app.onboarding.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class VerificationEventConsumer {
  private final VerificationService verificationService;
  private final ObjectMapper objectMapper;
  private final VerificationEventPublisher eventPublisher;

  @SneakyThrows
  @KafkaListener(topics = {
      "${app.kafka.topic.applicant-created}",
      "${app.kafka.topic.applicant-updated}"
  }, groupId = "${spring.kafka.consumer.group-id}")
  public void onApplicantChanged(final String message) {
    final Applicant applicant = objectMapper.readValue(message, Applicant.class);
    final VerificationStatus verificationStatus = verificationService.verify(applicant);
    eventPublisher.publishVerificationEvent(applicant, verificationStatus);
  }
}
