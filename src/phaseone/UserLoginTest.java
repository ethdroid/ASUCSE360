import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import phaseone.User;
import phaseone.UserLogin;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginTest {
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        // Assume UserLogin does not initialize the GUI components in the constructor.
        userLogin = new UserLogin();
        
        // Manually adding users for testing purposes.
        User adminUser = new User("adminUser", "password123", "Admin");
        User normalUser = new User("normalUser", "password456", "Student");
        
        // Adding the users to the static list.
        UserLogin.users.clear();  // Ensure there are no leftover users from previous tests.
        UserLogin.users.add(adminUser);
        UserLogin.users.add(normalUser);
    }

    @Test
    void testFindUserByUsername() {
        User foundUser = userLogin.findUserByUsername("adminUser");
        assertNotNull(foundUser, "User should be found");
        assertEquals("adminUser", foundUser.getUsername(), "Username should match the searched username");
    }

    @Test
    void testFindUserByUsernameNotFound() {
        User foundUser = userLogin.findUserByUsername("nonexistentUser");
        assertNull(foundUser, "User should not be found");
    }

    @Test
    void testGenerateInviteCode() {
        String inviteCode = userLogin.generateInviteCode();
        assertNotNull(inviteCode, "Invite code should not be null");
        assertFalse(inviteCode.isEmpty(), "Invite code should not be empty");
    }

    @Test
    void testGenerateOneTimePassword() {
        String otp = userLogin.generateOneTimePassword();
        assertNotNull(otp, "One-time password should not be null");
        assertFalse(otp.isEmpty(), "One-time password should not be empty");
    }

    @Test
    void testAddUser() {
        User newUser = new User("newUser", "password789");
        UserLogin.users.add(newUser);
        assertEquals(3, UserLogin.users.size(), "User list should contain 3 users");
        assertEquals("newUser", UserLogin.users.get(2).getUsername(), "The newly added user should be found");
    }

    @Test
    void testDeleteUser() {
        User userToDelete = userLogin.findUserByUsername("normalUser");
        assertNotNull(userToDelete, "User to delete should be found");
        UserLogin.users.remove(userToDelete);
        assertEquals(1, UserLogin.users.size(), "User list should contain 1 user after deletion");
        assertNull(userLogin.findUserByUsername("normalUser"), "Deleted user should no longer be found");
    }
}
