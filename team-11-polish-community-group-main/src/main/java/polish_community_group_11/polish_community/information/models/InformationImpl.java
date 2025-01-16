package polish_community_group_11.polish_community.information.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 Abstract implementation of the Information interface.
 This class provides common attributes and methods for managing information records.
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class InformationImpl implements Information {
    private int informationId;
    private String infoTitle;
    private String createdBy;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String infoDescription;
}
