package polish_community_group_11.polish_community.comments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer id;
    private String content;
    private int userId;
    private int postId;
    private String userEmail;
    private String username;
    private LocalDateTime createdDate;

    // Constructor without id for creating new comments
    public Comment(String content, int userId, int postId, String userEmail, String username, LocalDateTime createdDate) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
        this.userEmail = userEmail;
        this.username = username;
        this.createdDate = createdDate;
    }
}
