package at.ac.tuwien.ase.blockhain.bridge;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final Contact GENERIC_CONTACT = new Contact("Kevin Haller", "", "");

  private final ApiInfo API_INFO;
  private final String version = "1.0.0";

  public SwaggerConfig() {
    API_INFO = new ApiInfo("Exploiter Management", "Manages data about the exploiter.",
        version, "urn:tos", GENERIC_CONTACT, "MIT", "https://opensource.org/licenses/MIT",
        new ArrayList<VendorExtension>());
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(API_INFO)
        .produces(Collections.singleton("application/json"))
        .consumes(Collections.singleton("application/json"));
  }

}
