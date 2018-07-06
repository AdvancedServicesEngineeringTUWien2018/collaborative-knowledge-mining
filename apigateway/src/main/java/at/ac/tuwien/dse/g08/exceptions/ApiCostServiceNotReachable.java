package at.ac.tuwien.dse.g08.exceptions;

/**
 *
 */
public class ApiCostServiceNotReachable extends RuntimeException {

  public ApiCostServiceNotReachable() {
  }

  public ApiCostServiceNotReachable(String message) {
    super(message);
  }

  public ApiCostServiceNotReachable(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiCostServiceNotReachable(Throwable cause) {
    super(cause);
  }
}
