package polish_community_group_11.polish_community.admin.services;

import polish_community_group_11.polish_community.admin.models.AdminBoard;
import polish_community_group_11.polish_community.admin.models.ManageUser;
import polish_community_group_11.polish_community.register.models.User;

import java.sql.SQLException;
import java.util.List;

public interface AdminService {
    List<ManageUser> getUserManagementInfo() throws SQLException;
    AdminBoard getBoardManagementInfo() throws SQLException;
    void updateUserRole(User user, String roleName);
    void enableOrDisableUser(User user, Boolean enabled);
}
