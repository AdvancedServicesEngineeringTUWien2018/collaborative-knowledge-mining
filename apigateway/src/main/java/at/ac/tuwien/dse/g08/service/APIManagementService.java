package at.ac.tuwien.dse.g08.service;

import at.ac.tuwien.dse.g08.dto.APICallDto;
import at.ac.tuwien.dse.g08.exceptions.ApiCostServiceNotReachable;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Kevin Haller
 * @version 1.0
 * @since 1.0
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class APIManagementService {

  @Value("${exploiter.manager.api.address}")
  private String exploiterManagerApiAddress;
  @Value("${cost.estimator.api.address}")
  private String costEstimatorAPIAddress;

  private RestTemplate restTemplate;

  public void setRestTemplate(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  @Cacheable("apicost")
  public Long getCostOfPayload(String payload) throws ApiCostServiceNotReachable {
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
    ResponseEntity<Long> responseEntity = restTemplate
        .postForEntity(costEstimatorAPIAddress + "/estimate/cost/of/explorationflow", requestEntity,
            Long.class);
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return responseEntity.getBody();
    } else {
      throw new ApiCostServiceNotReachable(responseEntity.getStatusCode().getReasonPhrase());
    }
  }

  /**
   * Sends information of an API call to the exploiter management.
   *
   * @param username which called the API method.
   * @param cost estimated for the API method.
   * @param payload of the API method.
   */
  public void sendApiCallInformation(String username, Long cost, String payload) {
    APICallDto apiCallDto = new APICallDto();
    apiCallDto.setUid(username);
    apiCallDto.setCost(cost);
    apiCallDto.setPayload(payload);
    apiCallDto.setTimestamp(Instant.now().getEpochSecond());
    /* prepare the response */
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<APICallDto> requestEntity = new HttpEntity<>(apiCallDto, headers);
    restTemplate.postForEntity(
        exploiterManagerApiAddress + "/exploiter/{username}/call", requestEntity,
        Void.class, username);
  }

}
