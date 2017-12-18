package nl.kantilever.webwinkel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @GetMapping("/")
  public String helloWorld() {
    return "Hello World!" + multiply(5, 4);
  }

  public int multiply(int x, int y) {
    return x * y;
  }

}
