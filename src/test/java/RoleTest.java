import cashier.dao.entity.Role;
import cashier.dao.entity.User;
import cashier.util.StringHelpers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Hryniuk, created on  13.10.2020
 * email : hryniuk.t@gmail.com
 */
public class RoleTest {

    @Test
    public void nullOrEmpty(){
        User user = new User();
        user.setRole(0);
        assertEquals(Role.CASHIER, Role.getRole(user));
    }

    @Test
    public void nullOrEmpty1(){
        User user = new User();
        user.setRole(0);
        assertEquals(Role.CASHIER.getName(), Role.CASHIER.getName());
    }

}
