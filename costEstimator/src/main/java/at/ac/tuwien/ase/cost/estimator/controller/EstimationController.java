package at.ac.tuwien.ase.cost.estimator.controller;

import at.ac.tuwien.ase.cost.estimator.dto.ExplorationFlowDto;
import at.ac.tuwien.ase.cost.estimator.service.ExplorationFlowCostEstimationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estimate")
@Api(value = "Endpoint for cost estimations", description = "Allows to estimate the cost for exploration flows and other API calls.")
public class EstimationController {

  private ExplorationFlowCostEstimationService explorationFlowCostEstimationService;

  @Autowired
  public EstimationController(
      ExplorationFlowCostEstimationService explorationFlowCostEstimationService) {
    this.explorationFlowCostEstimationService = explorationFlowCostEstimationService;
  }

  @PostMapping(value = "/cost/of/explorationflow", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Estimates the cost of the given exploration flow.")
  public Long estimateCostOfExplorationFlows(@RequestBody ExplorationFlowDto explorationFlow) {
    return explorationFlowCostEstimationService.estimateCostOf(explorationFlow);
  }

}
