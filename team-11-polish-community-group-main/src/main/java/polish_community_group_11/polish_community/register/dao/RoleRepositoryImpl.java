package polish_community_group_11.polish_community.register.dao;

import polish_community_group_11.polish_community.register.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Fetch a role by its ID
    public Role findRoleById(int roleId) {
        String sql = "SELECT id, role_name FROM roles WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roleId}, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setName(rs.getString("role_name"));
            return role;
        });
    }

    // Fetch all roles (for displaying in registration form)
    public List<Role> findAllRoles() {
        String sql = "SELECT id, role_name FROM roles";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setName(rs.getString("role_name"));
            return role;
        });
    }
}