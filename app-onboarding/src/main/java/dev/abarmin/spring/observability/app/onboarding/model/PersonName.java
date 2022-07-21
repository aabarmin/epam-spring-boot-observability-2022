package dev.abarmin.spring.observability.app.onboarding.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * @author Aleksandr Barmin
 */
@Data
@Embeddable
public class PersonName {
  @Column(name = "applicant_first_name")
  private String firstName;

  @Column(name = "applicant_last_name")
  private String lastName;

  @Column(name = "applicant_given_name")
  private String givenName;
}
