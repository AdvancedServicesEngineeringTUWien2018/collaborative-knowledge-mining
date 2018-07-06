package at.ac.tuwien.dse.g08;

import at.ac.tuwien.dse.g08.filters.APICallFilterConfig;
import at.ac.tuwien.dse.g08.filters.APICallPostBillingFilterFactory;
import at.ac.tuwien.dse.g08.filters.APIExplorationFlowCostCovered;
import at.ac.tuwien.dse.g08.service.APIManagementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@SpringBootApplication
public class Application {

  @Bean
  public RouteLocator getRoutes(RouteLocatorBuilder builder,
      APIManagementService apiManagementService,
      RestTemplateBuilder restTemplateBuilder,
      APICallPostBillingFilterFactory apiCallPostBillingFilterFactory,
      @Value("${exploiter.manager.api.address}") String exploiterManagerAPIAddress,
      @Value("${cost.estimator.api.address}") String costEstimatorAPIAddress,
      @Value("${exploratory.search.api.address}") String exploratorySearchAPIAddress) {
    apiManagementService.setRestTemplate(restTemplateBuilder);
    return builder.routes()
        /* for registering a new exploiter, which can be done without credentials. */
        .route("exploiter/registration", r -> r.path("/exploiter").and()
            .method(HttpMethod.POST)
            .uri(exploiterManagerAPIAddress))
        /* gets the total number of credit for the given username */
        .route("exploiter/getCredits", r -> r.path("/exploiter/**/credits").and()
            .method(HttpMethod.GET)
            .uri(exploiterManagerAPIAddress))
        /* gets the deposit record for the given username */
        .route("exploiter/getDeposits", r -> r.path("/exploiter/**/credit/deposits").and()
            .method(HttpMethod.GET)
            .uri(exploiterManagerAPIAddress))
        /* calls the exploratory search api, which has to be paid for, needs exploiter id. */
        .route("ess/exploreWithFLow",
            r -> r.path("/explore/with/custom/flow").and().method(HttpMethod.POST)
                .and().predicate(
                    new APIExplorationFlowCostCovered(exploiterManagerAPIAddress,
                        apiManagementService,
                        restTemplateBuilder))
                .filters(
                    f -> f.filter(apiCallPostBillingFilterFactory.apply(new APICallFilterConfig())))
                .uri(exploratorySearchAPIAddress))
        /* call the estimator to estimate the cost of an exploration flow */
        .route("estimator/getCostOfExplorationFlow",
            r -> r.path("/estimate/cost/of/explorationflow").and().method(HttpMethod.POST)
                .uri(costEstimatorAPIAddress))
        .build();
  }

  public static void main(String args[]) {
    SpringApplication.run(Application.class, args);
  }

}
