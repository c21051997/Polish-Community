package polish_community_group_11.polish_community.information.models.ViewModel;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*
    This is a view model class DB Info form to take form data and perform validations.
*/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DBInfoForm {
    private int informationId;
    private int categoryId;
    private int tagId;

    // Add validations for not empty fields
    @NotEmpty(message = "Please enter a title for the new information heading")
    private String infoTitle;
    @NotEmpty(message = "Please enter a short description")
    private String shortDescription;
    @NotEmpty(message = "Please add the detailed content information for the new information.")
    private String infoDescription;

    private String createdBy;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
