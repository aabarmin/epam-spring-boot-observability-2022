package dev.abarmin.spring.observability.app.onboarding.repository;

import dev.abarmin.spring.observability.app.onboarding.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Aleksandr Barmin
 */
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
}
