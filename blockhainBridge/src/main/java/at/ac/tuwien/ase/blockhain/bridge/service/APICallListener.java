package at.ac.tuwien.ase.blockhain.bridge.service;

import at.ac.tuwien.ase.blockhain.bridge.dto.APICallDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.Subscription;
import java.math.BigInteger;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * This method
 *
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
@Service
public class APICallListener {

  private static final Logger logger = LoggerFactory.getLogger(APICallListener.class);

  @Value("${pubsub.topic.apicalls}")
  private String apiCallTopicName;
  @Value("${pubsub.unique.name}")
  private String subscriptionPrefix;

  private PubSubAdmin pubSubAdmin;
  private PubSubTemplate pubSubTemplate;
  private ObjectMapper objectMapper;

  private KnowledgeGraphCoinWeb3 knowledgeGraphCoinWeb3;

  @Autowired
  public APICallListener(KnowledgeGraphCoinWeb3 knowledgeGraphCoinWeb3, PubSubAdmin pubSubAdmin,
      PubSubTemplate pubSubTemplate,
      ObjectMapper objectMapper) {
    this.knowledgeGraphCoinWeb3 = knowledgeGraphCoinWeb3;
    this.pubSubAdmin = pubSubAdmin;
    this.pubSubTemplate = pubSubTemplate;
    this.objectMapper = objectMapper;
  }

  @PostConstruct
  public void setUp() {
    String depositSubscriptionName = this.subscriptionPrefix + "-" + this.apiCallTopicName;
    Optional<Subscription> depositTopicOptional = this.pubSubAdmin.listSubscriptions().stream()
        .filter(sub -> {
          String[] split = sub.getName().split("/");
          return split[split.length - 1].equals(depositSubscriptionName);
        }).findFirst();
    if (!depositTopicOptional.isPresent()) {
      this.pubSubAdmin.createSubscription(depositSubscriptionName, this.apiCallTopicName);
    }
    this.pubSubTemplate
        .subscribe(this.subscriptionPrefix + "-" + this.apiCallTopicName, new MessageReceiver() {
          @Override
          public void receiveMessage(PubsubMessage pubsubMessage,
              AckReplyConsumer ackReplyConsumer) {
            try {
              logger.info("Received API call {} from pub/sub topic {}.",
                  pubsubMessage.getData().toStringUtf8(), apiCallTopicName);
              APICallDto apiCallDto = objectMapper
                  .readValue(pubsubMessage.getData().toStringUtf8(), APICallDto.class);
              if (apiCallDto.getUid() != null) {
                TransactionReceipt receipt = knowledgeGraphCoinWeb3.getContract()
                    .reduceAPICredit(apiCallDto.getUid(), BigInteger.valueOf(apiCallDto.getCost()),
                        apiCallDto.getPayload(), BigInteger.valueOf(apiCallDto.getTimestamp()))
                    .send();
                logger.info("Received receipt {} from the smart contract", receipt);
              } else {
                logger.error("The sent api call message {} is corrupted.",
                    pubsubMessage.getData().toStringUtf8());
              }
              ackReplyConsumer.ack();
            } catch (Exception e) {
              ackReplyConsumer.nack();
            }
          }
        });
  }

  @PreDestroy
  public void tearDown() {
    pubSubAdmin.deleteSubscription(this.subscriptionPrefix + "-" + this.apiCallTopicName);
  }
}
