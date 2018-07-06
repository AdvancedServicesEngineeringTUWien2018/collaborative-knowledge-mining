package at.ac.tuwien.ase.blockhain.bridge.service;

import at.ac.tuwien.ase.blockhain.bridge.dto.APICreditDepositEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import java.nio.charset.Charset;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;

@Service
public class APICreditDepositEthListener {

  private static final Logger logger = LoggerFactory.getLogger(APICreditDepositEthListener.class);

  @Value("${pubsub.topic.apicredits}")
  private String apiCreditsDepositTopicName;

  private PubSubTemplate pubSubTemplate;
  private KnowledgeGraphCoinWeb3 knowledgeGraphCoinWeb3;
  private ObjectMapper objectMapper;

  @Autowired
  public APICreditDepositEthListener(PubSubTemplate pubSubTemplate,
      KnowledgeGraphCoinWeb3 knowledgeGraphCoinWeb3, ObjectMapper objectMapper) {
    this.pubSubTemplate = pubSubTemplate;
    this.knowledgeGraphCoinWeb3 = knowledgeGraphCoinWeb3;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void setUp() {
    logger.info("Start listening to API credit deposit events on the Ethereum blockchain.");
    knowledgeGraphCoinWeb3.getContract()
        .aPICreditDepositEventEventObservable(DefaultBlockParameterName.EARLIEST,
            DefaultBlockParameterName.LATEST).subscribe(deposit -> {
      APICreditDepositEventDto eventDto = new APICreditDepositEventDto();
      eventDto.setUid(deposit.uid);
      eventDto.setAmount(deposit.amount.longValue());
      eventDto.setTimestamp(deposit.timestamp.longValue());
      eventDto.setSenderAddress(deposit.sender);
      eventDto.setHash(Hex.encodeHexString(deposit.hash));
      logger.info("Received deposit event {}.", eventDto);
      try {
        this.pubSubTemplate
            .publish(apiCreditsDepositTopicName, PubsubMessage.newBuilder().setData(ByteString
                .copyFrom(objectMapper.writeValueAsString(eventDto), Charset.forName("utf-8")))
                .build());
      } catch (JsonProcessingException e) {
        logger.error("Could not set api credit deposit event {}, due to error: {}.", eventDto,
            e.getMessage());
      }
    });
  }

  @PreDestroy
  public void tearDown() {

  }
}
