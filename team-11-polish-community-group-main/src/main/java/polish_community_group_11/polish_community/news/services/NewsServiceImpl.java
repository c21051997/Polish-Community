package polish_community_group_11.polish_community.news.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.news.dao.NewsRepository;
import polish_community_group_11.polish_community.news.models.News;

import java.sql.SQLException;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;
    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository){
        this.newsRepository=newsRepository;
    }
    @Override
    public List<News> getAllNews() throws SQLException, EmptyResultDataAccessException {
        return newsRepository.getAllNews();
    }

    @Override

    public void addNews(News news)throws SQLException {
        newsRepository.addNews(news);
    }


    public News getNewsById(int id) throws SQLException {
        return newsRepository.getNewsById(id);
    }

    @Override
    public void updateNews(News news) throws SQLException {
        // link to the repository that directly interactes with the database
        newsRepository.updateNews(news);
    }

    @Override
    public void deleteNews(int id) throws SQLException {
        newsRepository.deleteNews(id);
    }

}
