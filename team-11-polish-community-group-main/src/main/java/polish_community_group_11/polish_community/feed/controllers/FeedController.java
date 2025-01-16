package polish_community_group_11.polish_community.feed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FeedController {

    @GetMapping("/feed")
    public ModelAndView getFeedPage() {
        ModelAndView modelAndView = new ModelAndView("feed/feed");
        return modelAndView;
    }
}
