package dev.abarmin.spring.observability.app.onboarding.event.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import dev.abarmin.spring.observability.app.onboarding.model.VerificationStatus;
import dev.abarmin.spring.observability.app.onboarding.model.event.VerificationDoneEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Aleksandr Barmin
 */
@Component
@RequiredArgsConstructor
public class VerificationEventPublisher {
  private final ObjectMapper objectMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${app.kafka.topic.applicant-verified}")
  private String verificationStatusTopic;

  @SneakyThrows
  public ListenableFuture<SendResult<String, String>> publishVerificationEvent(
      final Applicant applicant,
      final VerificationStatus verificationStatus) {

    final VerificationDoneEvent event = VerificationDoneEvent.builder()
        .applicantId(applicant.getApplicantId())
        .status(verificationStatus)
        .build();

    final String eventString = objectMapper.writeValueAsString(event);
    return kafkaTemplate.send(verificationStatusTopic, eventString);
  }
}
