package polish_community_group_11.polish_community.Categories;

import jakarta.persistence.*;

@Entity // marking the class as a table in the db
@Table(name = "categories") // name of table
public class Categories {

    @Id // showing primary key of table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generated
    private int categoryId;

    @Column(nullable = false, length = 255)
    private String categoryTitle;

    @Column(nullable = false, length = 255)
    private String categoryDescription;

    @Column(nullable = true)
    private int userId;

    // Getters and Setters
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

