package polish_community_group_11.polish_community.information.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController {
    @GetMapping("/search")
    public ModelAndView search(){
        ModelAndView modelAndView = new ModelAndView("search/searchBar");
        return modelAndView;
    }
}
