package polish_community_group_11.polish_community.admin.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import polish_community_group_11.polish_community.admin.services.AdminService;
import polish_community_group_11.polish_community.register.services.RoleService;

import java.sql.SQLException;

@Controller
public class AdminController {
    private final AdminService adminService;
    private final RoleService roleService;
    private Authentication authentication;
    public AdminController(AdminService adminService, RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping("admin/userInfo")
    public ModelAndView getFirstAdminUsers() throws SQLException, BadRequestException {
        ModelAndView mav = new ModelAndView("admin/userManage");
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_ADMIN")))
        {
            mav.addObject("usersAdminInfo",adminService.getUserManagementInfo());
            mav.addObject("roles",roleService.findAllRoles());
        }
        else{
            throw new BadRequestException("User is not admin");
        }
        return mav;
    }
    @GetMapping("admin")
    public ModelAndView getAdminDashboard() throws SQLException, BadRequestException {
        ModelAndView mav = new ModelAndView("admin/adminBoard");
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_ADMIN"))){
            mav.addObject("adminBoard",adminService.getBoardManagementInfo());
        }
        else{
            throw new BadRequestException("User is not admin");
        }
        return mav;
    }
}
