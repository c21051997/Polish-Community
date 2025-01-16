package polish_community_group_11.polish_community.comments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import polish_community_group_11.polish_community.comments.models.Comment;
import polish_community_group_11.polish_community.comments.services.CommentService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment. RANDOM_PORT)
@AutoConfigureMockMvc
class CommentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CommentService commentService;
    @Test
    @WithUserDetails("user@email.com")
    void whenUserPublishesACommentThenTheCommentIsSaved() throws Exception {
        // given a user is logged in

        // when they add a comment to a post
        mockMvc.perform(MockMvcRequestBuilders.post("/feed/comments/comments/publish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is really great and meaningful\", \"postId\":1}") )
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

        // then the comment exists in the database
        List<Comment> comments = commentService.findAllCommentsByPostId(1);
        assertThat(comments).hasSizeGreaterThan(0)
                .anySatisfy(comment -> assertThat(comment.getContent()).isEqualTo("This is really great and meaningful"));
    }
}