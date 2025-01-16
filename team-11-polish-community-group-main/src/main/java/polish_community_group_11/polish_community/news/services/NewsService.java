package polish_community_group_11.polish_community.news.services;

import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.news.models.News;

import java.sql.SQLException;
import java.util.List;

public interface NewsService {
    public List<News> getAllNews() throws SQLException;

    // Save new news article
    void addNews(News news) throws SQLException;


    News getNewsById(int id) throws SQLException;
    void updateNews(News news) throws SQLException;
    void deleteNews(int id) throws SQLException;

}
