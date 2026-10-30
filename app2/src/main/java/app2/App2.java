package app2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@SpringBootApplication
@RestController
public class App2 {
  private static final Logger log = LoggerFactory.getLogger(App2.class);

  public static void main(String[] args) {
    SpringApplication.run(App2.class, args);
  }

  @GetMapping("/")
  String hello() {
    OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("ID token: {}", user.getIdToken().getTokenValue());
    Map<?, ?> tokenResponse = RestClient.create().post() // app2 asks a token for itself, no user involved
        .uri("http://localhost:8180/realms/lesson/protocol/openid-connect/token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body("grant_type=client_credentials&client_id=app2&client_secret=app2-secret")
        .retrieve().body(Map.class);
    String accessToken = (String) tokenResponse.get("access_token");
    log.info("Own access token -> ms: {}", accessToken);
    String msResponse = RestClient.create().get().uri("http://localhost:8083/")
        .header("Authorization", "Bearer " + accessToken)
        .retrieve().body(String.class);
    return "app2: Hello " + user.getPreferredUsername() + "! " + msResponse;
  }
}
