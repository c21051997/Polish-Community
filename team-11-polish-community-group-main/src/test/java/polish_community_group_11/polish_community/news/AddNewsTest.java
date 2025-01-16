package polish_community_group_11.polish_community.news;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import polish_community_group_11.polish_community.news.models.NewsImpl;
import polish_community_group_11.polish_community.news.services.NewsService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class AddNewsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NewsService newsService;

    private NewsImpl validNews;
    private NewsImpl invalidNews;

    @BeforeEach
    public void setup() {
        validNews = new NewsImpl();
        validNews.setNews_title("Valid News Title");
        validNews.setNews_summary("Summary of valid news");
        validNews.setNews_link("https://www.msn.com/en-in/news/India/currency-notes-in-rajya-sabha-diversion-tactics-alleges-congress-bjp-seeks-probe/ar-AA1vnPFP?ocid=BingNewsSerp");
    }

    @Test
    public void testAddNewsValid() throws Exception {
        // Mock the NewsService to simulate saving news
        doNothing().when(newsService).addNews(any(NewsImpl.class));

        // Perform the POST request for adding valid news
        mockMvc.perform(post("/news/add")
                        .param("news_title", validNews.getNews_title())
                        .param("news_summary", validNews.getNews_summary())
                        .param("news_link", validNews.getNews_link()))
                .andExpect(status().is3xxRedirection())  // Expect redirect (successful)
                .andExpect(view().name("redirect:/news"))  // Should redirect to /news
                .andExpect(model().attributeDoesNotExist("news"));  // No validation errors on "news"

        // Verify that the service method was called once
        verify(newsService, times(1)).addNews(any(NewsImpl.class));
    }
}
