package nl.kantilever.webwinkel.controllers;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HelloWorldControllerTest {

  private HelloWorldController helloWorld = new HelloWorldController();

  @Test
  public void testMultiply() {
    int answer = helloWorld.multiply(5, 4);

    assertThat(answer, is(20));
  }

}
