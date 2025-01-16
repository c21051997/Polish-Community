package polish_community_group_11.polish_community.translation.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.translation.model.Translation;

import java.util.List;

@Repository
public class TranslationRepositoryImpl implements TranslationRepository {
    private static final Logger logger = LoggerFactory.getLogger(TranslationRepositoryImpl.class);

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Translation> translationMapper;

    public TranslationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.translationMapper = (rs, rowNum) -> {
            Translation translation = new Translation();
            translation.setId(rs.getInt("id"));
            translation.setKey(rs.getString("translation_key"));
            translation.setLanguage(rs.getString("language"));
            translation.setValue(rs.getString("value"));
            return translation;
        };
        // Test database connection on startup
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.info("Database connection successful");
        } catch (Exception e) {
            logger.error("Database connection failed", e);
        }
    }

    @Override
    public List<Translation> getTranslationsByLanguage(String language) {
        try {
            logger.info("Attempting to fetch translations for language: {}", language);
            String sql = "SELECT * FROM translations WHERE language = ?";
            List<Translation> results = jdbcTemplate.query(sql, translationMapper, language);
            logger.info("Found {} translations for language {}", results.size(), language);
            return results;
        } catch (Exception e) {
            logger.error("Error fetching translations for language {}: {}", language, e.getMessage());
            throw e;
        }
    }

    // getting all translaions
    @Override
    public List<Translation> getAllTranslations() {
        String sql = "SELECT * FROM translations";
        return jdbcTemplate.query(sql, translationMapper);
    }

    // get translation by key and language
    @Override
    public Translation getTranslationByKeyAndLanguage(String key, String language) {
        String sql = "SELECT * FROM translations WHERE translation_key = ? AND language = ?";
        return jdbcTemplate.queryForObject(sql, translationMapper, key, language);
    }

    // Add a new translation
    @Override
    public void addTranslation(Translation translation) {
        String sql = "INSERT INTO translations (translation_key, language, translation_value) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                translation.getKey(),
                translation.getLanguage(),
                translation.getValue()
        );
    }

    // update existing translation
    @Override
    public void updateTranslation(int id, Translation translation) {
        String sql = "UPDATE translations SET translation_key = ?, language = ?, translation_value = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                translation.getKey(),
                translation.getLanguage(),
                translation.getValue(),
                id
        );
    }

    // delete a translation
    @Override
    public void deleteTranslation(int id) {
        String sql = "DELETE FROM translations WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // getting all unique keys for word list management
    @Override
    public List<String> getAllTranslationKeys() {
        String sql = "SELECT DISTINCT translation_key FROM translations";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("translation_key"));
    }

    // get translations for something across all languages
    @Override
    public List<Translation> getTranslationsByKey(String key) {
        String sql = "SELECT * FROM translations WHERE translation_key = ?";
        return jdbcTemplate.query(sql, translationMapper, key);
    }
}
