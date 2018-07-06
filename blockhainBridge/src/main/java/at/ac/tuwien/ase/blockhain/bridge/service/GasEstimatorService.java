package at.ac.tuwien.ase.blockhain.bridge.service;

import java.math.BigInteger;
import org.springframework.stereotype.Service;

@Service
public class GasEstimatorService {

  /**
   * Gets the gas price that shall be used.
   *
   * @return the gas price that shall be used.
   */
  public BigInteger getGasPrice() {
    return new BigInteger("1000000000", 10);
  }

  /**
   * Gets the gas limit that shall be used.
   *
   * @return the gas limit that shall be used.
   */
  public BigInteger getGasLimit() {
    return new BigInteger("7400000", 10);
  }

}
