package polish_community_group_11.polish_community.feed.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import polish_community_group_11.polish_community.feed.models.FeedImpl;
import polish_community_group_11.polish_community.feed.repository.FeedRepository;
import polish_community_group_11.polish_community.feed.services.FeedService;
import polish_community_group_11.polish_community.feed.services.FileStorageService;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedApisController {
    private final FeedRepository feedRepository;

    private final UserService userService;
    private final FeedService feedService;
    private final FileStorageService fileStorageService;

    private static final Logger log = LoggerFactory.getLogger(FeedApisController.class);


    public FeedApisController(FeedRepository feedRepository, FeedService feedService, UserService userService, FileStorageService fileStorageService) {
        this.feedService = feedService;
        this.feedRepository = feedRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
    }

    // getting all posts
    @GetMapping
    public List<FeedImpl> getAllPosts() {
        log.info("Fetching all posts");
        //  getting current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;

        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            String email = auth.getName();
            log.info("User Email: " + email);
            currentUser = userService.findByEmail(email);
            log.info("Current User: " + currentUser);
        }

        List<FeedImpl> posts = feedRepository.getAllPosts();


        if (currentUser != null){
            // set isDeletable flag and isEditable flag for each post depedant on whether they are an admin or own the post
            for (FeedImpl post : posts) {
                log.info("Role ID: " + currentUser.getRoleId());
                boolean isSuperAdmin = currentUser != null && currentUser.getRoleId() == 1;
                log.info("IsSuperAdmin: " + isSuperAdmin);
                boolean isOwner = currentUser != null && post.getUserId() == currentUser.getId();
                log.info("IsPostOwner: " + isOwner);
                post.setIsDeletable(isSuperAdmin || isOwner);
                post.setIsEditable( isOwner);
                log.info("PostIsEditable: " + post.getIsDeletable());
            }
        }

        return posts;
    }

    //getting them by id
    @GetMapping("/{postId}")
    public ResponseEntity<FeedImpl> getPostById(@PathVariable int postId) {
        try {
            FeedImpl post = feedRepository.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // creating a new post
    @PostMapping("/add")
    public ResponseEntity<?> addNewPost(
            @RequestPart("post") FeedImpl feed,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            if (image != null && !image.isEmpty()) {
                String imageUrl = fileStorageService.storeFile(image);
                feed.setPostImageUrl(imageUrl);
            }
            feedService.addNewFeedPost(feed);
            return ResponseEntity.ok().body("Post created successfully");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating post: " + e.getMessage());
        }
    }



    // updating the post
    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @PathVariable int postId,
            @RequestPart("post") FeedImpl feed,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {

            if (image != null && !image.isEmpty()) {

                String imageUrl = fileStorageService.storeFile(image);
                feed.setPostImageUrl(imageUrl);
            } else {

                FeedImpl existingPost = feedRepository.getPostById(postId);
                if (existingPost != null) {
                    feed.setPostImageUrl(existingPost.getPostImageUrl());
                }
            }
            
            feedRepository.updatePost(postId, feed);
            return ResponseEntity.ok().body("Post updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating post: " + e.getMessage());
        }
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable int postId) {
        try {
            // get logged in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            FeedImpl post = feedRepository.getPostById(postId);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            // get current user
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User currentUser = userService.findByEmail(userDetails.getUsername());

            // check if admin or post owner
            boolean isSuperAdmin = currentUser.getRoleId() == 1;
            boolean isPostOwner = post.getUserId() == currentUser.getId();

            if (!isSuperAdmin && !isPostOwner) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            feedRepository.deletePost(postId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // liking a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable int postId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByEmail(auth.getName());
            feedRepository.likePost(postId, currentUser.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // remove like from post
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable int postId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByEmail(auth.getName());
            feedRepository.unlikePost(postId, currentUser.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // check if already liked
    @GetMapping("/{postId}/hasLiked")
    public ResponseEntity<Boolean> hasUserLikedPost(@PathVariable int postId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userService.findByEmail(auth.getName());
            boolean hasLiked = feedRepository.hasUserLikedPost(postId, currentUser.getId());
            return ResponseEntity.ok(hasLiked);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}