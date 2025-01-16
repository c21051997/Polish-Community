package polish_community_group_11.polish_community.translation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import polish_community_group_11.polish_community.translation.model.Translation;
import polish_community_group_11.polish_community.translation.service.TranslationService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TranslationApisController.class)
class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    private List<Translation> mockTranslations;

    @BeforeEach
    void setUp() {
        mockTranslations = Arrays.asList(
                new Translation(1, "greeting", "en", "Hello"),
                new Translation(2, "greeting", "pl", "Cześć")
        );
    }

    @Test
    void getAllTranslations_shouldReturnAllTranslations() throws Exception {

        when(translationService.getAllTranslations()).thenReturn(mockTranslations);
        mockMvc.perform(get("/api/translations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].key", is("greeting")))
                .andExpect(jsonPath("$[0].value", is("Hello")));
    }

    @Test
    void getTranslationsByLanguage_shouldReturnTranslationsForSpecificLanguage() throws Exception {
        List<Translation> englishTranslations = List.of(
                new Translation(1, "greeting", "en", "Hello")
        );

        when(translationService.getTranslationsByLanguage("en")).thenReturn(englishTranslations);

        mockMvc.perform(get("/api/translations/language/en")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].value", is("Hello")));
    }

    @Test
    void addTranslation_shouldAddNewTranslation() throws Exception {
        Translation newTranslation = new Translation(0, "farewell", "en", "Goodbye");


        Mockito.doNothing().when(translationService).addTranslation(Mockito.any(Translation.class));

        mockMvc.perform(post("/api/translations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"key\":\"farewell\",\"language\":\"en\",\"value\":\"Goodbye\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Translation added successfully."));
    }


}
