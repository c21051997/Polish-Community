package polish_community_group_11.polish_community.profile.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import polish_community_group_11.polish_community.profile.models.Profile;
import polish_community_group_11.polish_community.profile.services.ProfileService;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;

@RestController
public class ProfileRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile-json")
    public Profile getProfile(Authentication authentication) {
        return getProfileForAuthenticatedUser(authentication);
    }
    private Profile getProfileForAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();

        User user = userService.findByEmail(username);
        Profile profile = profileService.getProfile(user.getId());
        return profile;
    }
}
