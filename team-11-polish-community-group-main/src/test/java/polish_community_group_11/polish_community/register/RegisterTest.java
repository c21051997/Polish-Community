package polish_community_group_11.polish_community.register;

import polish_community_group_11.polish_community.register.dao.UserRepository;
import polish_community_group_11.polish_community.register.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;


@SpringBootTest
@Transactional
public class RegisterTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Create and save 3 users to the database before each test
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setPassword("password1");
        user1.setFullname("User One");
        user1.setDateOfBirth(LocalDate.of(2003, 1, 1));
        user1.setRoleId(1);
        userRepository.saveUser(user1);

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setPassword("password2");
        user2.setFullname("User Two");
        user2.setDateOfBirth(LocalDate.of(2003, 2, 2));
        user2.setRoleId(2);
        userRepository.saveUser(user2);

        User user3 = new User();
        user3.setEmail("user3@example.com");
        user3.setPassword("password3");
        user3.setFullname("User Three");
        user3.setDateOfBirth(LocalDate.of(2003, 3, 3));
        user3.setRoleId(1);
        userRepository.saveUser(user3);

        System.out.println("3 users have been saved to the database.");
    }

    @Test
    public void testSaveUser() {
        // create a new user 
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setFullname("New User");
        newUser.setPassword("password4");
        newUser.setDateOfBirth(LocalDate.of(2000, 1, 1));
        newUser.setRoleId(1);

        // call the saveUser method from the repository
        int userId = userRepository.saveUser(newUser); 

        // print out the saved user's ID
        System.out.println("Saved user ID: " + userId);

        // verify that the returned user ID is greater than 0, meaning the user was saved
        assertTrue(userId > 0, "User ID is greater than 0 therefore it was a successful insert");
        
    }


    @Test
    public void testFindAllUsers() {
        // call the repository method to retrieve all users
        List<User> users = userRepository.findAllUsers();

        // print out all users
        System.out.println("Users list:");
        for (User user : users) {
            System.out.println(user.getFullname() + " - " + user.getEmail());
        }

        // verify that the result is not null and contains the expected number of users
        assertNotNull(users);
        assertEquals(4, users.size()); 

        // verify that the users have the correct details
        for (User user : users) {
            assertNotNull(user.getEmail());
            assertNotNull(user.getFullname());
        }

        System.out.println("Test completed: Found " + users.size() + " users.");
    }


    @Test
    public void testFindByEmail() {
        
        // intialiaze the testing emails
        List<String> emails = Arrays.asList("user1@example.com", "user2@example.com", "user3@example.com");
        
        // loop through each email
        for (String email : emails) {
            // call the repository method to retrieve user by email
            User userByEmail = userRepository.findByEmail(email);

            if (userByEmail != null) {
                System.out.println("User found by email: " + userByEmail.getFullname() + " - " + userByEmail.getEmail());
            } else {
                System.out.println("No user found with the email " + email);
            }

            // verify that the user is returned
            assertNotNull(userByEmail);
            assertEquals(email, userByEmail.getEmail());
        }

        System.out.println("Test completed: Found 3 users");

        User wrongUser = userRepository.findByEmail("nonexistent@example.com");

        // ensure that no user is found when
        assertNull(wrongUser);
    }
}
