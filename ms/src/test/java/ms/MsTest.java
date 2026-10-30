package ms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Ms.class)
class MsTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  void greetsTheTokenOwner() throws Exception {
    mockMvc.perform(get("/").with(jwt().jwt(token -> token.claim("preferred_username", "user"))))
        .andExpect(content().string("ms: Hello user!"));
  }

  @Test
  void rejectsCallsWithoutToken() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isUnauthorized());
  }
}
