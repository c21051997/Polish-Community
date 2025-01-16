package polish_community_group_11.polish_community.comments.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.comments.dao.CommentRepository;
import polish_community_group_11.polish_community.comments.models.Comment;
import polish_community_group_11.polish_community.feed.models.Feed;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.dao.UserRepository;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private CommentRepository commentRepository;

    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> findAllCommentsByPostId(int postId) {
        LOG.info("Finding comments for: {}", postId);
        return commentRepository.findByPostId(postId);
    }
    public Comment publishComment(Feed feed, String content) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail);
        Comment comment = new Comment(
                content,
                user.getId(),
                feed.getPostId(),
                userEmail,
                user.getFullname(),
                LocalDateTime.now()
        );
        return commentRepository.save(comment);
    }
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}
