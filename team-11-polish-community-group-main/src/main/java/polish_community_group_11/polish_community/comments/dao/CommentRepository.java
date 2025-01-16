package polish_community_group_11.polish_community.comments.dao;

import polish_community_group_11.polish_community.comments.models.Comment;
import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> findByPostId(int postId);
    void deleteById(Integer id);

}
