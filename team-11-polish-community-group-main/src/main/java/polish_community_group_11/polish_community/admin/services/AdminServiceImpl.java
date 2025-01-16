package polish_community_group_11.polish_community.admin.services;

import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.admin.dao.AdminRepository;
import polish_community_group_11.polish_community.admin.models.AdminBoard;
import polish_community_group_11.polish_community.admin.models.ManageUser;
import polish_community_group_11.polish_community.register.models.User;

import java.sql.SQLException;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public List<ManageUser> getUserManagementInfo() throws SQLException {
        return adminRepository.getUserManagementInfo();
    }
    public AdminBoard getBoardManagementInfo() throws SQLException{
        return adminRepository.getBoardManagementInfo();
    }

    public void updateUserRole(User user, String roleName){
        int newRoleId=roleName.toLowerCase().contains("admin")?1:2;
        boolean needsUpdate=user.getRoleId()==newRoleId?false:true;
        if(needsUpdate){
            user.setRoleId(newRoleId);
            adminRepository.updateUserRole(user);
        }
    }

    public void enableOrDisableUser(User user, Boolean enabled){
        boolean needsUpdate=(enabled==user.getEnabled())?false:true;
        if(needsUpdate){
            user.setEnabled(enabled);
            adminRepository.updateUserRole(user);
        }
    }
}
