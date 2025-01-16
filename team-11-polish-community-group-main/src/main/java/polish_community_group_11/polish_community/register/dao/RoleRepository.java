package polish_community_group_11.polish_community.register.dao;

import polish_community_group_11.polish_community.register.models.Role;
import java.util.List;

public interface RoleRepository {
    Role findRoleById(int roleId);
    List<Role> findAllRoles();
}