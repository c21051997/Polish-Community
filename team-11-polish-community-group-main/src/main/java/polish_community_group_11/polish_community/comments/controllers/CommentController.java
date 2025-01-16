package polish_community_group_11.polish_community.comments.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import polish_community_group_11.polish_community.comments.models.Comment;
import polish_community_group_11.polish_community.comments.models.NewComment;
import polish_community_group_11.polish_community.comments.services.CommentService;
import polish_community_group_11.polish_community.feed.models.Feed;
import polish_community_group_11.polish_community.feed.services.FeedService;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping("/feed/comments")
//@RequiredArgsConstructor
public class CommentController {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CommentService commentService;
    private final FeedService feedService;

    @Autowired
    public CommentController(CommentService commentService, FeedService feedService) {
        this.commentService = commentService;
        this.feedService = feedService;
    }

    @PostMapping("/comments/publish")
    public ResponseEntity<Comment> publishComment(@RequestBody NewComment newComment) {

        LOG.info("Received new comment {}", newComment);
        Feed feed = feedService.getFeedById(newComment.getPostId());
        Comment comment = commentService.publishComment(feed, newComment.getContent());
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
