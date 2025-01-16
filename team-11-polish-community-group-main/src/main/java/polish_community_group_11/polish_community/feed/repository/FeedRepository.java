package polish_community_group_11.polish_community.feed.repository;

import polish_community_group_11.polish_community.feed.models.Feed;
import polish_community_group_11.polish_community.feed.models.FeedImpl;

import java.util.List;

public interface FeedRepository {
    List<FeedImpl> getAllPosts();

    FeedImpl getPostById(int postId);

    void addNewFeedPost(FeedImpl feed);

    void updatePost(int postId, FeedImpl feed);

    void deletePost(int postId);

    void likePost(int postId, int userId);

    void unlikePost(int postId, int userId);

    boolean hasUserLikedPost(int postId, int userId);
}
