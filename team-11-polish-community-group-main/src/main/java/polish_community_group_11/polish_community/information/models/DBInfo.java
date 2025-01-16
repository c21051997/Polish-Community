package polish_community_group_11.polish_community.information.models;

/**
 DBInfo interface represents a contract for database information entities.
 It extends the Information interface, adding methods specific to category
 and tag identifiers for the information records.
 */

public interface DBInfo extends Information {
    public int getCategoryId();
    public void setCategoryId(int categoryId);
    public int getTagId();
    public void setTagId(int tagId);
    public String getShortDescription();
    public void setShortDescription(String shortDescription);
}
