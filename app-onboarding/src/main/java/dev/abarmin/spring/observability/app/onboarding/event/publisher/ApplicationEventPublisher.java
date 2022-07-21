package dev.abarmin.spring.observability.app.onboarding.event.publisher;

import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import lombok.RequiredArgsConstructor;
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
public class ApplicationEventPublisher {
  private final KafkaTemplate kafkaTemplate;

  @Value("${app.kafka.topic.applicant-created}")
  private String applicantCreatedTopic;

  @Value("${app.kafka.topic.applicant-updated}")
  private String applicantUpdatedTopic;

  public ListenableFuture<SendResult<String, String>> applicantCreated(final Applicant applicant) {
    return kafkaTemplate.send(
        applicantCreatedTopic,
        applicant
    );
  }

  public ListenableFuture<SendResult<String, String>> applicantUpdated(final Applicant applicant) {
    return kafkaTemplate.send(
        applicantUpdatedTopic,
        applicant
    );
  }
}
