package polish_community_group_11.polish_community.feed.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.feed.models.Feed;
import polish_community_group_11.polish_community.feed.models.FeedImpl;
import polish_community_group_11.polish_community.feed.repository.FeedRepository;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
public class  FeedService {
    private final FeedRepository feedRepository;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(FeedService.class);

    public FeedService(FeedRepository feedRepository, UserService userService) {
        this.feedRepository = feedRepository;
        this.userService = userService;
    }


    public void addNewFeedPost(FeedImpl feed) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.error("No authentication found");
            throw new SecurityException("No authentication found");
        }

        String userEmail = authentication.getName();
        log.info("Adding new post for user: {}", userEmail);

        User user = userService.findByEmail(userEmail);
        if (user == null) {
            log.error("No user found for email: {}", userEmail);
            throw new SecurityException("No authenticated user found");
        }

        feed.setUserId(user.getId());
        feed.setPostTime(LocalDate.now());

        try {
            feedRepository.addNewFeedPost(feed);
        } catch (Exception e) {
            log.error("Error adding new post: ", e);
            throw e;
        }
    }

    public Feed getFeedById(int postId) {
        return feedRepository.getPostById(postId);
    }
}