package polish_community_group_11.polish_community.Categories.Pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller("categoriesPagesController")
public class PagesController {
    @GetMapping("/categories")
    public ModelAndView categories() {
        return new ModelAndView("categories/categories");
    }

    @GetMapping("/categories/{id}")
    public ModelAndView headings(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("headings/headings");
        modelAndView.addObject("categoryId", id);
        return modelAndView;
    }
}
