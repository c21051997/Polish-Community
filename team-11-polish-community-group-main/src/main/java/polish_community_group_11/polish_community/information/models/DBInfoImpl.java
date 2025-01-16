package polish_community_group_11.polish_community.information.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 Implementation of the DBInfo interface.
 Represents an information entity with additional attributes for category ID and tag ID.
 Extends the InformationImpl class to reuse common information-related attributes and behavior.
*/
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DBInfoImpl extends InformationImpl implements DBInfo {
    private int categoryId; // Id to map the information with Category table as a foreign key.
    private int tagId; // Id to map the information with tag table as a foreign key.
    private String shortDescription;

    public DBInfoImpl(int categoryId, int tagId, int informationId, String infoTitle,
                      String createdBy, LocalDate createdDate, LocalDate updatedDate,String shortDescription, String infoDescription){
        super(informationId, infoTitle,createdBy, createdDate, updatedDate, infoDescription);
        this.categoryId=categoryId;
        this.tagId=tagId;
        this.shortDescription=shortDescription;
    }

}
