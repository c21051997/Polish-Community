package polish_community_group_11.polish_community.Categories.Repository;

import polish_community_group_11.polish_community.Categories.Categories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    // will add more queries here as time goes by
}

