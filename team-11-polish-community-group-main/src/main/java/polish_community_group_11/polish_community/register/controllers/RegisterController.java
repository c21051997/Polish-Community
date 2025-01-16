package polish_community_group_11.polish_community.register.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import polish_community_group_11.polish_community.profile.models.Profile;
import polish_community_group_11.polish_community.profile.services.ProfileService;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.models.Role;
import polish_community_group_11.polish_community.register.services.UserService;
import polish_community_group_11.polish_community.register.services.RoleService;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ProfileService profileService;
    
    // displaying the registration form using get request and ModelAndView
    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        // create a ModelAndView object using the register.html file
        ModelAndView modelAndView = new ModelAndView("register/register");

        // add an empty User object to the model
        modelAndView.addObject("user", new User()); 

        // add all the roles currently in the roles table so the so the admin can create a account with a speicif role
        modelAndView.addObject("roles", roleService.findAllRoles());
        return modelAndView; 
    }

    // for handling form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam int roleId) {  
        
        // Set the role ID (instead of using the role enum)
        user.setRoleId(roleId);

        // check to see if the users email already exists
        if (userService.findByEmail(user.getEmail()) != null) {
            return "redirect:/register?error=email_taken";  
        }

        // save user to the database
        userService.saveUser(user);

//        Profile newProfile = new Profile();
//        newProfile.setUserId(user.getId());
//        profileService.addProfile(newProfile);

        // redirect to the login page
        return "redirect:/login"; 
    }
 
}