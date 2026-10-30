package e2e;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Needs the whole stack running: keycloak/start.sh, app1, app2, ms. */
class SsoTest {
  // a browser = follows redirects + remembers cookies
  HttpClient browser = HttpClient.newBuilder()
      .followRedirects(HttpClient.Redirect.ALWAYS)
      .cookieHandler(new LocalhostCookies())
      .build();

  @Test
  void loginOnceInApp1_thenEnterApp2WithoutPassword() throws Exception {
    String loginPage = get("http://localhost:8081/"); // app1 -> redirected to Keycloak
    Matcher formAction = Pattern.compile("action=\"([^\"]+)\"").matcher(loginPage);
    assertTrue(formAction.find(), "Expected Keycloak login form");

    String app1 = post(formAction.group(1).replace("&amp;", "&"), "username=user&password=user");
    assertEquals("app1: Hello user! ms: Hello user!", app1);

    assertEquals("app2: Hello user!", get("http://localhost:8082/")); // SSO: no login form
  }

  String get(String url) throws Exception {
    return browser.send(HttpRequest.newBuilder(URI.create(url)).build(), BodyHandlers.ofString()).body();
  }

  String post(String url, String form) throws Exception {
    return browser.send(HttpRequest.newBuilder(URI.create(url))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .POST(BodyPublishers.ofString(form)).build(), BodyHandlers.ofString()).body();
  }

  // Keycloak marks its cookies Secure: browsers still send them to http://localhost, Java doesn't
  static class LocalhostCookies extends CookieManager {
    @Override
    public void put(URI uri, Map<String, List<String>> headers) throws IOException {
      super.put(uri, headers.entrySet().stream().collect(toMap(Map.Entry::getKey,
          header -> header.getValue().stream().map(value -> value.replaceAll("(?i);\\s*Secure", "")).toList())));
    }
  }
}
