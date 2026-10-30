package ms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Ms {
  private static final Logger log = LoggerFactory.getLogger(Ms.class);

  public static void main(String[] args) {
    SpringApplication.run(Ms.class, args);
  }

  @GetMapping("/")
  String hello(@AuthenticationPrincipal Jwt jwt) {
    log.info("Received access token: {}", jwt.getTokenValue());
    return "ms: Hello " + jwt.getClaimAsString("preferred_username") + "!";
  }
}
