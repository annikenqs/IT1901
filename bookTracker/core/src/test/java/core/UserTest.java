package core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    public void setUp() {
        user1 = new User("suzanne@ntnu.no", "suzanne123", "jegerkul123");
        user2 = new User("johanne@ntnu.no", "johanne0504", "jegerbest03");
        user3 = new User();
    }

    @Test
    public void testGetEmail() {
        assertEquals("suzanne@ntnu.no", user1.getEmail());
        assertEquals("johanne@ntnu.no", user2.getEmail());
    }

    @Test
    public void testGetUsername() {
        assertEquals("suzanne123", user1.getUsername());
        assertEquals("johanne0504", user2.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("jegerkul123", user1.getPassword());
        assertEquals("jegerbest03", user2.getPassword());
    }

    @Test
    public void testSetUsername() {
        user3.setUsername("anniken12");
        assertEquals("anniken12", user3.getUsername());
    }

    @Test
    public void testSetEmail() {
        user3.setEmail("anniken@ntnu.no");
        assertEquals("anniken@ntnu.no", user3.getEmail());
    }

    @Test
    public void testFailsWhenSetPassword() {
        Assertions.assertThrows(IllegalArgumentException.class, 
        () -> new User("camilla@ntnu.no", "camilla123", "passordd"));
        Assertions.assertThrows(IllegalArgumentException.class, 
        () -> new User("camilla@ntnu.no", "camilla123", "pass123"));
        Assertions.assertThrows(IllegalArgumentException.class, 
        () -> new User("camilla@ntnu.no", "camilla123", "12345678"));
    }

}
