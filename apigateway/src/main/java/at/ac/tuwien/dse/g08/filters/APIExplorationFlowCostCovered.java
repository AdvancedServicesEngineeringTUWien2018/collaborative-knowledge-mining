package at.ac.tuwien.dse.g08.filters;

import at.ac.tuwien.dse.g08.service.APIManagementService;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

import java.util.function.Predicate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class APIExplorationFlowCostCovered implements Predicate<ServerWebExchange> {

  private String exploiterManagerApiAddress;
  private APIManagementService apiManagementService;
  private RestTemplate restTemplate;
  private StringBuilder stringBuilder;

  private LinkedBlockingQueue<Boolean> predicateResult;

  public APIExplorationFlowCostCovered(String exploiterManagerApiAddress,
      APIManagementService apiManagementService,
      RestTemplateBuilder restTemplateBuilder) {
    this.apiManagementService = apiManagementService;
    this.exploiterManagerApiAddress = exploiterManagerApiAddress;
    this.restTemplate = restTemplateBuilder.build();
    this.predicateResult = new LinkedBlockingQueue<>();
  }

  @Override
  public boolean test(ServerWebExchange serverWebExchange) {
    List<String> exploiterUsernameHeader = serverWebExchange.getRequest().getHeaders()
        .get("KGE-EXPLOITER-USERNAME");
    if (exploiterUsernameHeader != null && exploiterUsernameHeader.size() == 1) {
      String exploiterUsername = exploiterUsernameHeader.get(0);
      StringBuilder payloadBuilder = new StringBuilder();
      System.out.println(">>>>>>>>>>>>>>>>>>>");
      StringBuilder stringBuilder = new StringBuilder();
      /*serverWebExchange.getRequest().getBody().subscribe(dataBuffer -> {
        System.out.println(">>>" + dataBuffer.readableByteCount());
        byte[] data = new byte[dataBuffer.readableByteCount() - 1];
        System.out.println(">>> ------- ");
        dataBuffer.read(data, 0, dataBuffer.readableByteCount()-1);
        System.out.println(">>> -----");
        stringBuilder.append(new java.lang.String(data, Charset.forName("utf-8")));
        System.out.println(">>>" + stringBuilder.toString());

      });*/
      Long costOfPayload = 50L;//apiManagementService.getCostOfPayload(payloadBuilder.toString());
      ResponseEntity<Boolean> responseEntity = restTemplate
          .getForEntity(exploiterManagerApiAddress + "/exploiter/{username}/afford/cost/{costs}",
              Boolean.class, exploiterUsername, costOfPayload);
      if(responseEntity.getStatusCode().is2xxSuccessful()){
          return responseEntity.getBody() != null ? responseEntity.getBody() : false;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
