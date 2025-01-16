package polish_community_group_11.polish_community.feed.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedImpl implements Feed {
    private int postId;
    private String postImageUrl;
    private String postTitle;
    private String postDescription;
    private LocalDate postTime;
    private int userId;
    private String authorName;
    private String authorOrganization;
    private List<String> tags;
    private int likesCount;
    private boolean isDeletable;
    private boolean isEditable;

    @Override
    public boolean getIsDeletable() {
        return isDeletable;
    }

    @Override
    public void setIsDeletable(boolean isDeletable) {
        this.isDeletable = isDeletable;
    }

    @Override
    public boolean getIsEditable(){
        return isEditable;
    }

    @Override
    public void setIsEditable(boolean isEditable){
        this.isEditable = isEditable;
    }
}

