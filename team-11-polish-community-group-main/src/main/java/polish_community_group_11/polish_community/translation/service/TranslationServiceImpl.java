package polish_community_group_11.polish_community.translation.service;

import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.translation.model.Translation;
import polish_community_group_11.polish_community.translation.repository.TranslationRepository;

import java.util.List;

@Service
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;

    public TranslationServiceImpl(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public List<Translation> getAllTranslations() {
        return translationRepository.getAllTranslations();
    }

    @Override
    public List<Translation> getTranslationsByLanguage(String language) {
        return translationRepository.getTranslationsByLanguage(language);
    }

    @Override
    public Translation getTranslationByKeyAndLanguage(String key, String language) {
        return translationRepository.getTranslationByKeyAndLanguage(key, language);
    }

    @Override
    public void addTranslation(Translation translation) {
        translationRepository.addTranslation(translation);
    }

    @Override
    public void updateTranslation(int id, Translation translation) {
        translationRepository.updateTranslation(id, translation);
    }

    @Override
    public void deleteTranslation(int id) {
        translationRepository.deleteTranslation(id);
    }

    @Override
    public List<String> getAllTranslationKeys() {
        return translationRepository.getAllTranslationKeys();
    }

    @Override
    public List<Translation> getTranslationsByKey(String key) {
        return translationRepository.getTranslationsByKey(key);
    }
}
