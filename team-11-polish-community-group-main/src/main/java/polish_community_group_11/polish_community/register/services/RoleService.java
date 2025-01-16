package polish_community_group_11.polish_community.register.services;

import polish_community_group_11.polish_community.register.dao.RoleRepository;
import polish_community_group_11.polish_community.register.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAllRoles();
    }

    public Role findRoleById(int roleId) {
        return roleRepository.findRoleById(roleId);
    }
}
