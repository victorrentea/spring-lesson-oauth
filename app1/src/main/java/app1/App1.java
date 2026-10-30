package app1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@RestController
public class App1 {
  private static final Logger log = LoggerFactory.getLogger(App1.class);

  public static void main(String[] args) {
    SpringApplication.run(App1.class, args);
  }

  @GetMapping("/")
  String hello(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) { // the access token is not in SecurityContextHolder
    OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String accessToken = client.getAccessToken().getTokenValue();
    log.info("ID token: {}", user.getIdToken().getTokenValue());
    log.info("Access token -> ms: {}", accessToken);
    String msResponse = RestClient.create().get().uri("http://localhost:8083/")
        .header("Authorization", "Bearer " + accessToken)
        .retrieve().body(String.class);
    return "app1: Hello " + user.getPreferredUsername() + " (" + user.getEmail() + ")! " + msResponse;
  }
}
