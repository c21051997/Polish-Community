package polish_community_group_11.polish_community.news.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import polish_community_group_11.polish_community.news.services.NewsService;
import polish_community_group_11.polish_community.news.models.News;
import polish_community_group_11.polish_community.news.models.NewsImpl;

import java.sql.SQLException;
import java.time.LocalDate;

// controller for the edit news page
// this file is used for handling the displaying the page and for handling the buttons requests
@Controller
public class EditNewsController {

    private final NewsService newsService;

    @Autowired
    public EditNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // mapping for the edit page with the particular news id the user is editing
    @GetMapping("/editNews/{id}")
    public ModelAndView showEditNewsForm(@PathVariable("id") int id) throws SQLException {
        // fetch the news by the ID from the news service class
        News news = newsService.getNewsById(id); 
        
        // error checking
        // check to see if the news class is empty
        if (news == null) {
            throw new IllegalArgumentException("Invalid news ID: " + id);
        }

        // check to see if the date class is empty and if so then set a default date
        if (news.getNews_upload_date() == null) {
            news.setNews_upload_date(LocalDate.now());  
        }

        // logs to check if mapping is working correctly
        System.out.println("News ID: " + news.getNews_id());
        
        ModelAndView modelAndView = new ModelAndView("news/editNews"); 
        // add the news object to the model
        modelAndView.addObject("news", news); 
        return modelAndView; 
    }

    // post mapping for when the user submits the form
    // used @RequestParam due to @ModelAttribute not working
    // @RequestParam selects the fields from the form individually

    @PostMapping("/editNews/{id}")
    public String editOrDeleteNews(@RequestParam("news_id") int news_id,
        @RequestParam("news_title") String news_title,
        @RequestParam("news_summary") String news_summary,
        @RequestParam("news_source") String news_source,
        @RequestParam("news_link") String news_link,
        @RequestParam("news_image_url") String news_image_url,
        @RequestParam("user_id") int user_id,
        @RequestParam("news_upload_date") String news_upload_date,
        @RequestParam("action") String action) throws SQLException {
        
        // if the user clicks the edit button
        if (action.equals("edit")) {

            // create new instance of the news class with the data in the fields
            News news = new NewsImpl(news_id, news_title, news_summary, news_source, news_link,
                            news_image_url, user_id, LocalDate.parse(news_upload_date));
            // update the news using the news service class
            newsService.updateNews(news); 
            // redirect to the news list
            return "redirect:/news"; 
        } 

        // if the user clicks the delete button
        else if (action.equals("delete")) {
            // delete the news using the news service class
            newsService.deleteNews(news_id); 
            return "redirect:/news"; 
        }
        // error if the event is not recognised
        throw new IllegalArgumentException("Invalid action: " + action);
    }
}