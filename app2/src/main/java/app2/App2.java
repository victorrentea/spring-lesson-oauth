package app2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    return "app2: Hello " + user.getPreferredUsername() + "!";
  }
}
