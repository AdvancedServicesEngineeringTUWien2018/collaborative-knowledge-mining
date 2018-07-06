package at.ac.tuwien.ase.blockhain.bridge.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the APICreditDeposit event from the Knowledge Graph smart contract.
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
public class APICreditDepositEventDto {

  private String uid;
  private String senderAddress;
  private long amount;
  private long timestamp;
  private String hash;

  public APICreditDepositEventDto() {

  }

  @JsonCreator
  public APICreditDepositEventDto(@JsonProperty("uid") String uid,
      @JsonProperty("senderAddress") String senderAddress, @JsonProperty("amount") long amount,
      @JsonProperty("timestamp") long timestamp,
      @JsonProperty("hash") String hash) {
    this.uid = uid;
    this.senderAddress = senderAddress;
    this.amount = amount;
    this.timestamp = timestamp;
    this.hash = hash;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getSenderAddress() {
    return senderAddress;
  }

  public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
  }

  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  @Override
  public String toString() {
    return "APICreditDepositEventDto{" +
        "uid='" + uid + '\'' +
        ", senderAddress='" + senderAddress + '\'' +
        ", amount=" + amount +
        ", timestamp=" + timestamp +
        ", hash=" + hash +
        '}';
  }
}
