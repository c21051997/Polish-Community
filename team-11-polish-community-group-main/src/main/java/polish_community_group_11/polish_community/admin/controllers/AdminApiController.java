package polish_community_group_11.polish_community.admin.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import polish_community_group_11.polish_community.admin.services.AdminService;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;

@RestController
public class AdminApiController {
    private final AdminService adminService;
    private final UserService userService;
    private Authentication authentication;

    public AdminApiController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @PutMapping("/admin/edit/{user_id}/role")
    public ResponseEntity<Void> changeUserRole(@PathVariable("user_id") int user_id,
                                               @RequestBody String role_name) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (role_name == null || role_name.trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            try {
                User user = userService.findById(user_id);
                if (user == null) {
                    return ResponseEntity.notFound().build();
                }
                adminService.updateUserRole(user, role_name);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/admin/edit/{user_id}/enabled")
    public ResponseEntity<Void> enableOrDisableUser(
            @PathVariable("user_id") int user_id, @RequestParam Boolean enabled) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            try {
                User user = userService.findById(user_id);
                if (user == null) {
                    return ResponseEntity.notFound().build();  // User not found
                }
                adminService.enableOrDisableUser(user, enabled);
                return ResponseEntity.ok().build();
            } catch (IllegalArgumentException e) {
                // Invalid boolean or other illegal arguments
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/admin/get/{user_id}")
    public ResponseEntity<User> getUser(@PathVariable("user_id") int user_id) {
        try {
            User user = userService.findById(user_id);
            if (user == null) {
                return ResponseEntity.notFound().build();  // User not found
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
