package at.ac.tuwien.dse.g08.dto;

/**
 * This dto wraps the api call entity.
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
public class APICallDto {

  private String uid;
  private long cost;
  private String payload;
  private long timestamp;

  public APICallDto() {

  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public long getCost() {
    return cost;
  }

  public void setCost(long cost) {
    this.cost = cost;
  }

  public String getPayload() {
    return payload;
  }

  public void setPayload(String payload) {
    this.payload = payload;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    return "APICallDto{" +
        "uid='" + uid + '\'' +
        ", cost=" + cost +
        ", payload='" + payload + '\'' +
        ", timestamp=" + timestamp +
        '}';
  }
}
