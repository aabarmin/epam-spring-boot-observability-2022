package dev.abarmin.spring.observability.app.onboarding.model;

import java.time.LocalDate;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class IdentifyVerification {
  private LocalDate requestSent;
  private LocalDate responseReceived;
private String status;
}
