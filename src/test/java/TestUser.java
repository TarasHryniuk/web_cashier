import cashier.dao.entity.Product;
import cashier.dao.entity.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Hryniuk, created on  13.10.2020
 * email : hryniuk.t@gmail.com
 */
public class TestUser {

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals("User{id=1, active=true, login=login, role=0, fullName=fullName}", user.toString());
    }

    @Test
    public void testHashCode() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals(-1651799723, user.hashCode());
    }

    @Test
    public void testEquals() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals(true, user.equals(user));
    }

    @Test
    public void testGetId() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals(new Integer(1), user.getId());
    }

    @Test
    public void testGetActive() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals(true, user.isActive());
    }

    @Test
    public void testGetLogin() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals("login", user.getLogin());
    }

    @Test
    public void testGetAuthCode() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals("authCode", user.getAuthCode());
    }

    @Test
    public void testGetRole() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals(new Integer(0), user.getRole());
    }


    @Test
    public void testGetFullName() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals("fullName", user.getFullName());
    }

    @Test
    public void testGetRoleName() {
        User user = new User();
        user.setId(1);
        user.setActive(true);
        user.setLogin("login");
        user.setAuthCode("authCode");
        user.setRole(0);
        user.setFullName("fullName");
        user.setRoleName("roleName");

        assertEquals("roleName", user.getRoleName());
    }
}
