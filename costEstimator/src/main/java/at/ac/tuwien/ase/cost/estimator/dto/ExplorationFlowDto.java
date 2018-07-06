package at.ac.tuwien.ase.cost.estimator.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This dto wraps exploration flows.
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
public class ExplorationFlowDto {

  private List<ExplorationFlowStep> steps;

  @JsonCreator
  public ExplorationFlowDto(@JsonProperty("steps") List<ExplorationFlowStep> steps) {
    this.steps = steps;
  }

  public List<ExplorationFlowStep> getSteps() {
    return steps;
  }

  @Override
  public String toString() {
    return "ExplorationFlowDto{" +
        "steps=" + steps +
        '}';
  }
}

