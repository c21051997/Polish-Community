package polish_community_group_11.polish_community.contact.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class Contact {
    @GetMapping("/contactus")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("contact/contact");
        return modelAndView;
    }
}
