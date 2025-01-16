package polish_community_group_11.polish_community.comments.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String content;
    private String postTitle;
    private String createdDate;
    private String username;
}