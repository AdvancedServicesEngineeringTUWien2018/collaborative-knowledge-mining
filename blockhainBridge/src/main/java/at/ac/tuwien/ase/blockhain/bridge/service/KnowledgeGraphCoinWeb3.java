package at.ac.tuwien.ase.blockhain.bridge.service;

import at.ac.tuwien.ase.blockhain.bridge.KnowledgeGraphCoin;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;

@Service
public class KnowledgeGraphCoinWeb3 {

  private static final Logger logger = LoggerFactory.getLogger(KnowledgeGraphCoinWeb3.class);

  @Value("${ethereum.geth.rpc.address}")
  private String ethRpcAddress;
  @Value("${ethereum.kgcoin.address}")
  private String knowledgeGraphContractAddress;
  @Value("${ethereum.geth.account.operator.address}")
  private String operatorAddress;

  private Web3j web3j;
  private GasEstimatorService gasEstimatorService;
  private KnowledgeGraphCoin contract;

  @Autowired
  public KnowledgeGraphCoinWeb3(GasEstimatorService gasEstimatorService) {
    this.gasEstimatorService = gasEstimatorService;
  }

  @PostConstruct
  public void setUp() {
    logger.info("Tries to open a connection with the geth rpc at {}.", ethRpcAddress);
    web3j = Web3j.build(new HttpService(ethRpcAddress));
    contract = KnowledgeGraphCoin
        .load(knowledgeGraphContractAddress, web3j,
            new ClientTransactionManager(web3j, operatorAddress),
            gasEstimatorService.getGasPrice(), gasEstimatorService.getGasLimit());
  }

  public KnowledgeGraphCoin getContract() {
    return contract;
  }

  @PreDestroy
  public void tearDown() {
    if (web3j != null) {
      web3j.shutdown();
    }
  }

}
