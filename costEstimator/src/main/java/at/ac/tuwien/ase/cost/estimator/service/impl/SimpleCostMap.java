package at.ac.tuwien.ase.cost.estimator.service.impl;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class SimpleCostMap {

  private Long defaultValue;
  private Map<String, Long> steps;

  public Long getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(Long defaultValue) {
    this.defaultValue = defaultValue;
  }

  public void setSteps(Map<String, Long> steps) {
    this.steps = steps;
  }

  public Map<String, Long> getMap() {
    return steps;
  }

}
