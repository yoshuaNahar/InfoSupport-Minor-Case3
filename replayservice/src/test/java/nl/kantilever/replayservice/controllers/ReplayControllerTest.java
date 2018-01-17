package nl.kantilever.replayservice.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import nl.kantilever.replayservice.services.ReplayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(ReplayController.class)
public class ReplayControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReplayService replayService;

  @Test
  public void testGetAuditLog() throws Exception {
    this.mockMvc.perform(get("/replayRequest"))
      .andExpect(status().isOk());
  }

  @Test
  public void testReplayPostRequest() throws Exception {
    this.mockMvc.perform(get("/replayPostRequest"))
      .andExpect(status().isOk())
      .andExpect(content().string("Succes"));
  }

}
