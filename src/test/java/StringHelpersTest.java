import cashier.util.StringHelpers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Taras Hryniuk, created on  12.10.2020
 * email : hryniuk.t@gmail.com
 */
public class StringHelpersTest {

    @Test
    public void nullOrEmpty(){
        assertEquals(true, StringHelpers.isNullOrEmpty(""));
    }

    @Test
    public void isNumberString(){
        assertEquals(true, StringHelpers.isNumberString("1"));
    }

    @Test
    public void isNumberStringFalse(){
        assertNotEquals(false, StringHelpers.isNumberString("1"));
    }

    @Test
    public void digest(){
        assertEquals("c4ca4238a0b923820dcc509a6f75849b", StringHelpers.digest("1"));
    }
}
