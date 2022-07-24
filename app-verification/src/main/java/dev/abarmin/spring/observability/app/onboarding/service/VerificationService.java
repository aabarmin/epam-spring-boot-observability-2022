package dev.abarmin.spring.observability.app.onboarding.service;

import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import dev.abarmin.spring.observability.app.onboarding.model.PersonName;
import dev.abarmin.spring.observability.app.onboarding.model.VerificationStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Aleksandr Barmin
 */
@Service
public class VerificationService {
  public VerificationStatus verify(final Applicant applicant) {
    if (applicant == null || applicant.getPersonName() == null) {
      return VerificationStatus.REJECTED;
    }
    final PersonName personName = applicant.getPersonName();
    if (StringUtils.isAnyEmpty(
        personName.getFirstName(),
        personName.getGivenName(),
        personName.getLastName()
    )) {
      return VerificationStatus.REJECTED;
    }
    return VerificationStatus.VERIFIED;
  }
}
