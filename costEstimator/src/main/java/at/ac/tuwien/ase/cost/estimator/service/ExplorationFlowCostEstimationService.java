package at.ac.tuwien.ase.cost.estimator.service;

import at.ac.tuwien.ase.cost.estimator.dto.ExplorationFlowDto;
import org.springframework.stereotype.Service;

/**
 * Instances of this service estimate the cost of a exploration flow.
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
public interface ExplorationFlowCostEstimationService {

  /**
   * This method estimates the cost of computation for the given exploration flow.
   *
   * @param explorationFlowDto for which the costs shall be estimated.
   * @return the cost of the call.
   */
  Long estimateCostOf(ExplorationFlowDto explorationFlowDto);

}
