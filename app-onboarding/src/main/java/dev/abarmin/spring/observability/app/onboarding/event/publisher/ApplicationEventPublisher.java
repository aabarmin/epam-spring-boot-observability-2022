package dev.abarmin.spring.observability.app.onboarding.event.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Aleksandr Barmin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationEventPublisher {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Value("${app.kafka.topic.applicant-created}")
  private String applicantCreatedTopic;

  @Value("${app.kafka.topic.applicant-updated}")
  private String applicantUpdatedTopic;

  public ListenableFuture<SendResult<String, String>> applicantCreated(final Applicant applicant) {
    return this.sendMessage(applicantCreatedTopic, applicant);
  }

  public ListenableFuture<SendResult<String, String>> applicantUpdated(final Applicant applicant) {
    return this.sendMessage(applicantUpdatedTopic, applicant);
  }

  @SneakyThrows
  private ListenableFuture<SendResult<String, String>> sendMessage(final String topic,
                                                                   final Object data) {

    final String messageContent = objectMapper.writeValueAsString(data);

    log.info("Sending message to topic {} with content {}", topic, messageContent);

    return kafkaTemplate.send(
        topic,
        messageContent
    );
  }
}
