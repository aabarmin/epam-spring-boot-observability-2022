package dev.abarmin.spring.observability.app.onboarding.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Embeddable
public class IdentifyVerification {
  @Column(name = "idv_request_sent")
  private LocalDate requestSent;

  @Column(name = "idv_response_received")
  private LocalDate responseReceived;

  @Column(name = "idv_status")
  private String status;
}
