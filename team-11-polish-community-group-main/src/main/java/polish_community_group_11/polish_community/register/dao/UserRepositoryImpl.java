package polish_community_group_11.polish_community.register.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.register.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate; // JdbcTemplate is used to interact with the database

    // function for saving user
    public int saveUser(User user) {
        // sql query for inserting into users table
        String sql = "INSERT INTO users (email, password, fullname, dob, role_id) VALUES (?, ?, ?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, 
                user.getEmail(), 
                user.getPassword(),
                user.getFullname(),
                user.getDateOfBirth(),
                user.getRoleId());
                user.getOrganization();
                
        if (rowsAffected > 0) {
            // if user is successfully inserted, get the user ID
            String getUserIdSql = "SELECT LAST_INSERT_ID()";
            int userId = jdbcTemplate.queryForObject(getUserIdSql, Integer.class);
    
            // link the user to the role by inserting into the user_roles table 
            String userRolesSql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
            jdbcTemplate.update(userRolesSql, userId, user.getRoleId());
    
            return userId; // or return something else if needed
        }
    
        return -1;
    }

    // function for fetching all users
    public List<User> findAllUsers() {
        // sql query for selecting all users from users table
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFullname(rs.getString("fullname"));
            user.setDateOfBirth(rs.getObject("dob", LocalDate.class)); // attempt to get object by using localdate
            user.setRoleId(rs.getInt("role_id"));
            user.setOrganization(rs.getString("organization"));
            return user;
        });
    }

    // function for finding user by email
    public User findByEmail(String email) {
        // SQL query to find user by email
        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            // Using queryForObject to directly return the result as a single User object
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setDateOfBirth(rs.getObject("dob", LocalDate.class)); 
                user.setRoleId(rs.getInt("role_id"));
                user.setOrganization(rs.getString("organization"));
                return user;
            }, email);
        } catch (EmptyResultDataAccessException e) {
            // return null if no user is found with the email
            return null;
        }
    }

    public int findUserIdByEmail(String email) {
        if(email.trim().equals("")|| email == null){
            throw new IllegalArgumentException("Email is null or empty");
        }
        User user = findByEmail(email);
        if(user == null){
            throw new NullPointerException("User not found");
        }
        return user.getId();
    }

    public String findUserFullNameByEmail(String email) {
        User user = findByEmail(email);
        return user.getFullname();
    }


    // function for finding user by id
    public User findById(int id) {
        // sql query for finding a user by id
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            // Using queryForObject to directly return the result as a single User object
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setDateOfBirth(rs.getObject("dob", LocalDate.class)); 
                user.setRoleId(rs.getInt("role_id"));
                user.setOrganization(rs.getString("organization"));
                user.setEnabled(rs.getBoolean("enabled"));
                return user;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            // return null if no user is found with the id
            return null;
        }
    }

}
