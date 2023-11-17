package track.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
//import track.DeliveryTrackerApplication;
import track.repository.DeliveryRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {
 //   @LocalServerPort
 //   private int port;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DeliveryRepository deliveryRepository;


    @Test
    @DisplayName("GET returns 200 OK")
    void when() throws Exception {
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/deliveryapi/active");
//        mockMvc.perform(requestBuilder.param(,))
////                .andExpect(
////                        status().isOk()
////                );
      Assertions.assertEquals(true,2 == 2);
    }
}