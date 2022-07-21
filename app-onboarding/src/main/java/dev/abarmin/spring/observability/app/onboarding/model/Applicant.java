package dev.abarmin.spring.observability.app.onboarding.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Entity
@Table(name = "applicants")
public class Applicant {
  @Id
  @Column(name = "applicant_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long applicantId;

  @Embedded
  private PersonName personName;

  @Embedded
  private IdentifyVerification identifyVerification;
}
