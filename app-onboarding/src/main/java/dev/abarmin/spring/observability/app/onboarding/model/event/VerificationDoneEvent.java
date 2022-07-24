package dev.abarmin.spring.observability.app.onboarding.model.event;

import dev.abarmin.spring.observability.app.onboarding.model.VerificationStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Builder
public class VerificationDoneEvent {
  private Long applicantId;
  private VerificationStatus status;
}
