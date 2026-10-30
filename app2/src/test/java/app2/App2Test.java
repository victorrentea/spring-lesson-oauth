package app2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(App2.class)
class App2Test {
  @Autowired
  MockMvc mockMvc;
  @MockitoBean // instead of fetching the client config from Keycloak
  ClientRegistrationRepository clientRegistrations;

  @Test
  void sendsAnonymousToLogin() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().is3xxRedirection());
  }
}
