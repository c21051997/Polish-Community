package polish_community_group_11.polish_community.Categories.Service;

import polish_community_group_11.polish_community.Categories.Categories;
import polish_community_group_11.polish_community.Categories.Repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    public Optional<Categories> getCategoryById(int id) {
        return categoriesRepository.findById(id);
    }

    public Categories saveCategory(Categories category) {
        return categoriesRepository.save(category);
    }

    public void deleteCategory(int id) {
        categoriesRepository.deleteById(id);
    }
}
