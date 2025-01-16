package polish_community_group_11.polish_community.admin.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.admin.models.AdminBoard;
import polish_community_group_11.polish_community.admin.models.ManageUser;
import polish_community_group_11.polish_community.register.models.User;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.List;

@Repository
public class AdminRepositoryImpl implements AdminRepository {
    private JdbcTemplate jdbc; // JdbcTemplate instance for performing database operations
    private RowMapper<ManageUser> adminManageUserMapper; // RowMapper to map rows of the database result set to Manage User objects
    private RowMapper<AdminBoard> adminBoardMapper;
    public AdminRepositoryImpl(JdbcTemplate aJdbc){
        this.jdbc = aJdbc;
        setAdminManageUserMapper();
        setAdminBoardMapper();
    }

    public void setAdminManageUserMapper(){
        adminManageUserMapper = (rs, i) ->{
                return new ManageUser(
                        rs.getInt("id"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getBoolean("enabled"),
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                );
        };
    }
    public void setAdminBoardMapper(){
        adminBoardMapper = (rs, i) ->{
            return new AdminBoard(
                    rs.getInt("total_users"),
                    rs.getInt("total_posts"),
                    rs.getInt("upcoming_events"),
                    rs.getInt("new_comments")
            );
        };
    }

    public List<ManageUser> getUserManagementInfo() throws SQLException {
        String sql="select u.id, u.fullname, u.email, u.enabled,u.role_id, rol.role_name from users u" +
                " left join user_roles u_rol" +
                " on u.id = u_rol.user_id" +
                " join roles rol" +
                " on u.role_id = rol.id" +
                " order by u.id asc";
//                +
//                " limit 10";
        List<ManageUser> users=null;
        try{
            users=jdbc.query(sql, adminManageUserMapper);
        }
        catch(EmptyResultDataAccessException ex){
            // Handle case where no records were found
            throw new EmptyResultDataAccessException("Did not find any records with selected id", 0);
        }
        catch (IncorrectResultSizeDataAccessException ex) {
            // Handle case where multiple results were found
            throw new IncorrectResultSizeDataAccessException("Multiple records generated, only one record expected",1);
        }
        catch (DataAccessException e) {
            // Handle other database-related exceptions
            throw new SQLException("Some unexpected database error occured.");
        }
        return users;
    }

    public AdminBoard getBoardManagementInfo() throws SQLException {
        String sql="SELECT" +
                "(SELECT COUNT(id) FROM users) AS total_users," +
                "(SELECT COUNT(post_id) FROM feed) AS total_posts," +
                "(SELECT COUNT(event_id) FROM event WHERE event_date > CURRENT_DATE) AS upcoming_events," +
                "(SELECT COUNT(id) FROM comment WHERE DATEDIFF(CURRENT_DATE, created_date) <= 3) AS new_comments";
        AdminBoard dashboard=null;
        try{
            dashboard=jdbc.queryForObject(sql, adminBoardMapper);
        }
        catch (IncorrectResultSizeDataAccessException ex) {
            // Handle case where multiple results were found
            throw new IncorrectResultSizeDataAccessException("Multiple records generated, only one record expected",1);
        }
        catch (DataAccessException e) {
            // Handle other database-related exceptions
            throw new SQLException("Some unexpected database error occured.");
        }
        return dashboard;
    }

    public int updateUserRole(User user){
        // Validate input to ensure no null values are passed
        if (user == null || user.getId() <= 0 || user.getRoleId() <= 0) {
            throw new IllegalArgumentException("Invalid user or role details.");
        }
        // SQL query for updating the user's role and enabled status
        String sql = "UPDATE users SET role_id = ?, enabled = ? WHERE id = ?";

        try {
            // Perform the update operation
            int rowsUpdated = jdbc.update(sql, user.getRoleId(), user.getEnabled(), user.getId());
            return rowsUpdated;
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error occurred while updating user role.", e);
        }
    }
}
