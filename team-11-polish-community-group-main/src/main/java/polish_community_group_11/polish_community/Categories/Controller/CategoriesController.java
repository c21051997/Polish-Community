package polish_community_group_11.polish_community.Categories.Controller;



import polish_community_group_11.polish_community.Categories.Categories;
import polish_community_group_11.polish_community.Categories.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories") // all the apis to preface with api to differentiate from normal html ones
public class CategoriesController {

    @Autowired // still do not fully understand this, find out more/
    private CategoriesService categoriesService;

    @GetMapping
    public List<Categories> getAllCategories() {
        return categoriesService.getAllCategories();
    }

    // find a category by its id
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable int id) {
        return categoriesService.getCategoryById(id)
                .map(ResponseEntity::ok) // if a category is found map the request with ok 200
                .orElse(ResponseEntity.notFound().build()); // if not found map to 404
    }

    // create a new category
    @PostMapping("/add")
    public Categories createCategory(@RequestBody Categories category) {
        return categoriesService.saveCategory(category);
    }

    // update a category details.
    @PutMapping("/{id}")
    public ResponseEntity<Categories> updateCategory(@PathVariable int id, @RequestBody Categories category) {
        return categoriesService.getCategoryById(id)
                .map(existingCategory -> {
                    category.setCategoryId(id);
                    Categories updatedCategory = categoriesService.saveCategory(category);
                    return ResponseEntity.ok(updatedCategory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // getting rid of a category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        if (categoriesService.getCategoryById(id).isPresent()) {
            categoriesService.deleteCategory(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
