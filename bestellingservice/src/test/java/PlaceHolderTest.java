import nl.kantilever.bestellingservice.controllers.TestController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class PlaceHolderTest {

  TestController testController;

  @Before
  public void setup() {
    this.testController = testController;
  }

  @Test
  public void test() {
    int result = testController.multiply(4, 5);

    Assert.assertThat(result, is(20));
  }

}
