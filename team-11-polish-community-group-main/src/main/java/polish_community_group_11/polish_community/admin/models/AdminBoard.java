package polish_community_group_11.polish_community.admin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBoard {
    private int totalNoOfUsers;
    private int totalPosts;
    private int upcomingEvents;
    private int newComments;
}
