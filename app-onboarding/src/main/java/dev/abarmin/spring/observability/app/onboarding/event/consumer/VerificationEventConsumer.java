package dev.abarmin.spring.observability.app.onboarding.event.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.observability.app.onboarding.model.event.VerificationDoneEvent;
import dev.abarmin.spring.observability.app.onboarding.service.ApplicationService;
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
  private final ObjectMapper objectMapper;
  private final ApplicationService applicationService;

  @SneakyThrows
  @KafkaListener(topics = {
      "${app.kafka.topic.applicant-verified}"
  }, groupId = "${spring.kafka.consumer.group-id}")
  private void consume(final String message) {
    final VerificationDoneEvent event = objectMapper.readValue(message, VerificationDoneEvent.class);

    applicationService.findOne(event.getApplicantId())
        .map(applicant -> applicationService.updateVerification(
            applicant,
            event.getStatus()
        ))
        .orElseThrow(() -> new RuntimeException(String.format(
            "No applicant with id %s",
            event.getApplicantId()
        )));
  }
}
