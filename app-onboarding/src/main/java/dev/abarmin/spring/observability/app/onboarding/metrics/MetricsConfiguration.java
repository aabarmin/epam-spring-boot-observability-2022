package dev.abarmin.spring.observability.app.onboarding.metrics;

import dev.abarmin.spring.observability.app.onboarding.repository.ApplicantRepository;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Aleksandr Barmin
 */
@Configuration
@EnableAspectJAutoProxy
public class MetricsConfiguration {
  @Bean
  public TimedAspect timedAspect() {
    return new TimedAspect();
  }

  @Bean
  public MeterBinder numberOfApplicants(final ApplicantRepository repository) {
    return registry -> Gauge.builder("applicants.number", () -> repository.count())
        .baseUnit("Items")
        .description("Number of applicants in the app")
        .register(registry);
  }

  @Bean
  public Timer verificationTimer() {
    return Timer.builder("applicants.processing-time")
        .description("Time between processing request sent and received a response")
        .publishPercentiles(0.9, 0.95, 0.99)
        .register(Metrics.globalRegistry);
  }

  @Bean
  public MeterBinder saveTime() {
    return registry -> Timer.builder("applicants.save-time")
        .description("Time spend on saving an applicant")
        .register(registry);
  }
}
