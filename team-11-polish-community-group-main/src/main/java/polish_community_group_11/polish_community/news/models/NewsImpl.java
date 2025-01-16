package polish_community_group_11.polish_community.news.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsImpl implements News{
    private int news_id;
    @NotEmpty(message = "News Title should not be empty!")
    private String news_title;
    @NotEmpty(message = "News summary should not be empty!")
    private String news_summary;
    private String news_source;
    private String news_link;
    private String news_image_url;
    private int user_id;
    private LocalDate news_upload_date;
}