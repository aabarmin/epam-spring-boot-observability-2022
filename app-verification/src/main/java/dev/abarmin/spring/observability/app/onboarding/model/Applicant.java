package dev.abarmin.spring.observability.app.onboarding.model;

import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
public class Applicant {
  private Long applicantId;
  private PersonName personName;
  private IdentifyVerification identifyVerification;
}
