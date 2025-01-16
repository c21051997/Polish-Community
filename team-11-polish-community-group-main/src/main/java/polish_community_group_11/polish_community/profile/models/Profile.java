package polish_community_group_11.polish_community.profile.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    private int userId;
    private String fullName;
    private String email;
    private String profilePicture;
    private LocalDate dob;
    private String bio;
    private String phoneNumber;
    private String organisation;
    private boolean showPhoneNumber;
    private boolean showDob;
}
