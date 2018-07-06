package at.ac.tuwien.ase.cost.estimator.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class represents an exploration flow step.
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
public class ExplorationFlowStep {

  private String name;
  private JsonNode params;

  @JsonCreator
  public ExplorationFlowStep(@JsonProperty(value = "name", required = true) String name,
      @JsonProperty(value = "params", required = false) JsonNode params) {
    this.name = name;
    this.params = params;
  }

  public String getName() {
    return name;
  }

  public JsonNode getParams() {
    return params;
  }

  @Override
  public String toString() {
    return "ExplorationFlowStep{" +
        "name='" + name + '\'' +
        ", params=" + params +
        '}';
  }
}
