package polish_community_group_11.polish_community.profile.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import polish_community_group_11.polish_community.profile.models.Profile;
import polish_community_group_11.polish_community.profile.services.ProfileService;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class ProfileController {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public ModelAndView profile(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("profile/profilePage");

        Profile profile = getProfileForAuthenticatedUser(authentication);
        modelAndView.addObject("profile", profile);

        return modelAndView;
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute Profile profile, Authentication authentication,@RequestParam("newPicture") MultipartFile newPicture)
    throws IOException {
        String username = authentication.getName();
        User user = userService.findByEmail(username);
        profile.setUserId(user.getId());
        if (!newPicture.isEmpty()) {
            String fileName = StringUtils.cleanPath(newPicture.getOriginalFilename());
            String uploadDir = "build/resources/main/static/assets/user-photos/" + user.getId();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = newPicture.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                profile.setProfilePicture("/assets/user-photos/" + user.getId() + "/" + fileName);
            }
        }
        profileService.updateProfile(profile);
        return "redirect:/profile";
    }
    private Profile getProfileForAuthenticatedUser(Authentication authentication) {
        LOG.info("getting profile for {} - isAuthenticated: {}", authentication.getName(), authentication.isAuthenticated());
        String username = authentication.getName();

        User user = userService.findByEmail(username);
        Profile profile = profileService.getProfile(user.getId());
        return profile;
    }
}
