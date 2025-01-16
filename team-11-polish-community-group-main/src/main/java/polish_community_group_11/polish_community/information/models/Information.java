package polish_community_group_11.polish_community.information.models;

import java.time.LocalDate;

/**
 The Information interface defines a contract for managing information-related
 entities. It provides methods to get and set properties such as information ID,
 title, creator details, creation and update timestamps, and a description.
*/

public interface Information {
    public int getInformationId();
    public void setInformationId(int informationId);
    public String getInfoTitle();
    public void setInfoTitle(String infoTitle);
    public String getCreatedBy();
    public void setCreatedBy(String createdBy);
    public LocalDate getCreatedDate();
    public void setCreatedDate(LocalDate createdDate);
    public LocalDate getUpdatedDate();
    public void setUpdatedDate(LocalDate updatedDate);
    public String getInfoDescription();
    public void setInfoDescription(String infoDescription);
}
