package at.ac.tuwien.dse.g08.filters;

import at.ac.tuwien.dse.g08.service.APIManagementService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
public class APICallPostBillingFilterFactory extends
    AbstractGatewayFilterFactory<APICallFilterConfig> {


  private RestTemplateBuilder restTemplateBuilder;
  private APIManagementService apiManagementService;

  @Autowired
  public APICallPostBillingFilterFactory(APIManagementService apiManagementService,
      RestTemplateBuilder restTemplateBuilder) {
    this.restTemplateBuilder = restTemplateBuilder;
    this.apiManagementService = apiManagementService;
  }

  @Override
  public GatewayFilter apply(APICallFilterConfig config) {
    RestTemplate restTemplate = restTemplateBuilder.build();
    return (exchange, chain) -> {
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        ServerHttpResponse response = exchange.getResponse();
        if (response.getStatusCode() != null && !response.getStatusCode().is5xxServerError()) {
          List<String> exploiterUsernameHeader = exchange.getRequest().getHeaders()
              .get("KGE-EXPLOITER-USERNAME");
          if (exploiterUsernameHeader != null && exploiterUsernameHeader.size() == 1) {
            String exploiterUsername = exploiterUsernameHeader.get(0);
            StringBuilder payloadBuilder = new StringBuilder();
            //TODO: must read in payload from body.
            // Long costOfPayload = apiCostService.getCostOfPayload(payloadBuilder.toString());
            Long costOfPayload = 50L;
            /* wrap information of the API call */
            apiManagementService.sendApiCallInformation(exploiterUsername, costOfPayload, "");
          }
        }
      }));
    };
  }
}
