package at.ac.tuwien.ase.blockhain.bridge.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This dto wraps the api call message.
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

  @JsonCreator
  public APICallDto(@JsonProperty("uid") String uid, @JsonProperty("cost") long cost,
      @JsonProperty("payload") String payload, @JsonProperty("timestamp") long timestamp) {
    this.uid = uid;
    this.cost = cost;
    this.payload = payload;
    this.timestamp = timestamp;
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
