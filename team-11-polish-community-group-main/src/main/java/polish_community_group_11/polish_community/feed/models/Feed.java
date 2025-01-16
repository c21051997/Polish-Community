package polish_community_group_11.polish_community.feed.models;

import java.time.LocalDate;
import java.util.List;

public interface Feed {
    String getPostImageUrl();
    void setPostImageUrl(String postImageUrl);

    String getPostTitle();
    void setPostTitle(String postTitle);

    String getPostDescription();
    void setPostDescription(String postDescription);

    LocalDate getPostTime();
    void setPostTime(LocalDate postTime);

    int getUserId();
    void setUserId(int userId);

    String getAuthorName();
    void setAuthorName(String authorName);

    String getAuthorOrganization();
    void setAuthorOrganization(String organization);

    List<String> getTags();
    void setTags(List<String> tags);

    int getLikesCount();
    void setLikesCount(int likesCount);

    boolean getIsDeletable();
    void setIsDeletable(boolean isDeletable);

    boolean getIsEditable();
    void setIsEditable(boolean isEditable);

    int getPostId();



}