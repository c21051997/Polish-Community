package polish_community_group_11.polish_community.news.models;

import java.time.LocalDate;

public interface News {
    public int getNews_id();
    public void setNews_id(int news_id);
    public String getNews_title();
    public void setNews_title(String news_title);
    public String getNews_summary();
    public void setNews_summary(String news_summary);
    public String getNews_source();
    public void setNews_source(String news_source);
    public String getNews_link();
    public void setNews_link(String news_link);
    public int getUser_id();
    public void setUser_id(int user_id);
    public LocalDate getNews_upload_date();
    public void setNews_upload_date(LocalDate news_upload_date);
    public String getNews_image_url();
    public void setNews_image_url(String news_image_url);
}
