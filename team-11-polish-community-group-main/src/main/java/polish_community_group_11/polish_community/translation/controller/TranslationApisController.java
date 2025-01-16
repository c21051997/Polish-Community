package polish_community_group_11.polish_community.translation.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polish_community_group_11.polish_community.translation.model.Translation;
import polish_community_group_11.polish_community.translation.service.TranslationService;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
public class TranslationApisController {

    private final TranslationService translationService;

    public TranslationApisController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Translation API is working");
    }

    @PostConstruct
    public void init() {
        System.out.println("TranslationApisController initialized with mappings:");
        System.out.println("/api/translations");
        System.out.println("/api/translations/language/{language}");
    }

    // get all translations
    @GetMapping
    public ResponseEntity<List<Translation>> getAllTranslations() {
        List<Translation> translations = translationService.getAllTranslations();
        return ResponseEntity.ok(translations);
    }

    // for specific lang
    @GetMapping("/language/{language}")
    public ResponseEntity<List<Translation>> getTranslationsByLanguage(@PathVariable String language) {
        List<Translation> translations = translationService.getTranslationsByLanguage(language);
        return ResponseEntity.ok(translations);
    }

    // for specific key and lang
    @GetMapping("/{key}/language/{language}")
    public ResponseEntity<Translation> getTranslationByKeyAndLanguage(
            @PathVariable String key,
            @PathVariable String language) {
        Translation translation = translationService.getTranslationByKeyAndLanguage(key, language);
        if (translation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(translation);
    }

    // adding new translation
    @PostMapping
    public ResponseEntity<String> addTranslation(@RequestBody Translation translation) {
        translationService.addTranslation(translation);
        return ResponseEntity.ok("Translation added successfully.");
    }

    // update existing
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTranslation(@PathVariable int id, @RequestBody Translation translation) {
        translationService.updateTranslation(id, translation);
        return ResponseEntity.ok("Translation updated successfully.");
    }

    // deleting a translation
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTranslation(@PathVariable int id) {
        translationService.deleteTranslation(id);
        return ResponseEntity.ok("Translation deleted successfully.");
    }

    // get all unique keys
    @GetMapping("/keys")
    public ResponseEntity<List<String>> getAllTranslationKeys() {
        List<String> keys = translationService.getAllTranslationKeys();
        return ResponseEntity.ok(keys);
    }

    // get all translations for a specific key across all langs
    @GetMapping("/keys/{key}")
    public ResponseEntity<List<Translation>> getTranslationsByKey(@PathVariable String key) {
        List<Translation> translations = translationService.getTranslationsByKey(key);
        return ResponseEntity.ok(translations);
    }
}
