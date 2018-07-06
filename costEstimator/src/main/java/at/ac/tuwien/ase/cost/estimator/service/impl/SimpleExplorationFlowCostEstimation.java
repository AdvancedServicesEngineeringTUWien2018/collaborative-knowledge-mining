package at.ac.tuwien.ase.cost.estimator.service.impl;

import at.ac.tuwien.ase.cost.estimator.dto.ExplorationFlowDto;
import at.ac.tuwien.ase.cost.estimator.dto.ExplorationFlowStep;
import at.ac.tuwien.ase.cost.estimator.service.ExplorationFlowCostEstimationService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is a naive implementation of {@link SimpleExplorationFlowCostEstimation}.
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
@Service
public class SimpleExplorationFlowCostEstimation implements ExplorationFlowCostEstimationService {

  private static final Logger logger = LoggerFactory
      .getLogger(SimpleExplorationFlowCostEstimation.class);

  private Long defaultValue;
  private Map<String, Long> costMap;

  @Autowired
  public SimpleExplorationFlowCostEstimation(SimpleCostMap simpleCostMap) {
    this.costMap = simpleCostMap.getMap();
    this.defaultValue = simpleCostMap.getDefaultValue();
  }

  @Override
  public Long estimateCostOf(ExplorationFlowDto explorationFlowDto) {
    logger.info("Computes the costs of the exploration flow {}.", explorationFlowDto);
    long cost = 4L;
    for (ExplorationFlowStep step : explorationFlowDto.getSteps()) {
      if (costMap.containsKey(step.getName())) {
        cost += costMap.get(step.getName());
      } else {
        cost += defaultValue;
      }
    }
    logger.info("The computed cost for the exploration flow {} is {}.", explorationFlowDto, cost);
    return cost;
  }
}
