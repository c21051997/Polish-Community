package polish_community_group_11.polish_community.aboutUs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutUsController {

    @GetMapping("/aboutUs")
    public ModelAndView modelAndView() {

        ModelAndView modelAndView = new ModelAndView("aboutUs/aboutUs");
        return modelAndView;

    }
}
