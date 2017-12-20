import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class PlaceHolderTest {

  @Test
  public void test() {
    Assert.assertThat(20, is(4*5));
  }

}
