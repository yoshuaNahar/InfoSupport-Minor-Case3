package nl.kantilever.replayservice.controllers;

import nl.kantilever.replayservice.services.ReplayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplayController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private ReplayService replayService;

  @Autowired
  public ReplayController(ReplayService replayService) {
    this.replayService = replayService;
  }

  @GetMapping("/replayRequest")
  public ResponseEntity getAuditLog() {
    replayService.getAllEvents();
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/replayPostRequest")
  public String replayPostRequest() {
    replayService.replayEvents();
    logger.info("Succes");
    return "Succes";
  }

}
