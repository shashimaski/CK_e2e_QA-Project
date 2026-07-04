package hello;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void noParamGreetingShouldReturnDefaultMessage() throws Exception {
        mockMvc.perform(get("/greeting"))
                .andExpect(status().isOk());
    }

    @Test
    void paramGreetingShouldReturnTailoredMessage() throws Exception {
        mockMvc.perform(get("/greeting?name=John"))
                .andExpect(status().isOk());
    }
}
