package polish_community_group_11.polish_community.translation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import polish_community_group_11.polish_community.translation.model.Translation;
import polish_community_group_11.polish_community.translation.repository.TranslationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TranslationServiceImplTest {

    @Mock
    private TranslationRepository translationRepository;

    @InjectMocks
    private TranslationServiceImpl translationService;

    private Translation mockTranslation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockTranslation = new Translation(1, "greeting", "en", "Hello");
    }

    @Test
    void getAllTranslations_shouldReturnAllTranslations() {
        when(translationRepository.getAllTranslations()).thenReturn(List.of(mockTranslation));

        List<Translation> result = translationService.getAllTranslations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hello", result.get(0).getValue());
    }
}
