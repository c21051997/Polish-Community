package polish_community_group_11.polish_community.news.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.information.models.DBInfoImpl;
import polish_community_group_11.polish_community.news.models.News;
import polish_community_group_11.polish_community.news.models.NewsImpl;
import polish_community_group_11.polish_community.news.services.NewsService;

import java.sql.SQLException;

@Controller
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService){
        this.newsService=newsService;
    }

    @GetMapping("news")
    public ModelAndView getNews() throws SQLException {
        ModelAndView modelAndView = new ModelAndView("news/newsList");
        modelAndView.addObject("newsList",newsService.getAllNews());
        modelAndView.addObject("news" , new NewsImpl());
        return modelAndView;
    }

    @PostMapping("/news/add")
    public ModelAndView addInformation(@Valid @ModelAttribute("news") NewsImpl news,
                                       BindingResult bindingResult, Model model) throws SQLException {
        ModelAndView modelAndView = new ModelAndView("news/addNews");
        if(bindingResult.hasErrors()){
            modelAndView.addObject(model.asMap());
        }
        else{
            News newsToAdd = news;
            newsService.addNews(newsToAdd);
            modelAndView = new ModelAndView("redirect:/news");
        }
        return modelAndView;
    }
    }
