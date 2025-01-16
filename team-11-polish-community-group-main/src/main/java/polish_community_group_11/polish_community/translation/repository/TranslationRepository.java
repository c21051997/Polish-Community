package polish_community_group_11.polish_community.translation.repository;

import polish_community_group_11.polish_community.translation.model.Translation;

import java.util.List;

public interface TranslationRepository {


    List<Translation> getAllTranslations();


    List<Translation> getTranslationsByLanguage(String language);


    Translation getTranslationByKeyAndLanguage(String key, String language);


    void addTranslation(Translation translation);


    void updateTranslation(int id, Translation translation);


    void deleteTranslation(int id);


    List<String> getAllTranslationKeys();


    List<Translation> getTranslationsByKey(String key);
}
