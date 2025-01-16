package polish_community_group_11.polish_community.comments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import polish_community_group_11.polish_community.comments.services.CommentService;
import polish_community_group_11.polish_community.feed.repository.FeedRepositoryImpl;

@Controller
public class FeedWithCommentsController {
    private FeedRepositoryImpl feedRepository;
    private CommentService commentService;

    @Autowired
    public FeedWithCommentsController(FeedRepositoryImpl feedRepository, CommentService commentService){
        this.feedRepository = feedRepository;
        this.commentService = commentService;
    }

    @GetMapping("/feed-with-comments")
    public ModelAndView getFeed(){
        ModelAndView modelAndView = new ModelAndView("feed/feedWithComments");
        modelAndView.addObject("posts" , feedRepository.getAllPosts());
        // Fetch comments for each post and add to model
        modelAndView.addObject("commentService", commentService);
        return modelAndView;
    }

}
