package polish_community_group_11.polish_community.news.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.information.models.DBInfo;
import polish_community_group_11.polish_community.news.models.News;
import polish_community_group_11.polish_community.news.models.NewsImpl;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Repository // Marks the class as a repository in the Spring context, responsible for data access logic
public class NewsRepositoryImpl implements NewsRepository {
    private final JdbcTemplate jdbc; // JdbcTemplate instance for performing database operations
    private RowMapper<News> newsMapper; // RowMapper to map rows of the database result set to News objects

    @Autowired
    public NewsRepositoryImpl(JdbcTemplate aJdbc){
        this.jdbc = aJdbc;
        setInfoItemMapper(); // Method to configure the RowMapper for mapping database rows to News objects
    }
    public void setInfoItemMapper() {
        newsMapper = (rs, i) -> {
            try{
                // Check if any required fields are missing or null
                if(rs.getInt("news_id")==0
                    || rs.getString("news_title")==null
                    || rs.getString("news_source") == null
                    || rs.getDate("news_upload_date") == null){
                    throw new NullPointerException("Required News fields cannot be empty");
                }
                return new NewsImpl(
                        rs.getInt("news_id"),
                        rs.getString("news_title"),
                        rs.getString("news_summary"),
                        rs.getString("news_source"),
                        rs.getString("news_link"),
                        rs.getString("news_image_url"),
                        rs.getInt("user_id"),
                        rs.getDate("news_upload_date").toLocalDate()
                );
            }
            catch (NullPointerException ex) {
                // Handle NullPointerException if any required fields are missing or null
                throw new NullPointerException("Error in mapping News data: " + ex.getMessage());
            } catch (DateTimeException ex) {
                // Handle DateTimeException if the date format is invalid
                throw new RuntimeException("Invalid date format for News Upload Date");
            } catch (Exception ex) {
                // Handle any other unexpected exceptions
                throw new RuntimeException("Unexpected error during mapping: " + ex.getMessage(), ex);
            }
        };
    }

    // Method to retrieve all news from the database
    @Override
    public List<News> getAllNews() throws SQLException, EmptyResultDataAccessException {
        String sql = "select * from news order by news_upload_date desc";
        try {
            List<News> newsList = jdbc.query(sql, newsMapper);

            // Check if the news list is empty and throw an exception if it is
            if (newsList.isEmpty()) {
                throw new EmptyResultDataAccessException(0);
            }
            return newsList;
        }

        catch (EmptyResultDataAccessException e){
            // Handles the empty records error and throw a new one with a custom message
            throw new EmptyResultDataAccessException("The news table does not have any records.",0);
        }
        catch (DataAccessException e) {
            // Handle syntax errors in SQL exception and throw the message
            throw new SQLException("There is an error in your SQL syntax");
        }
    }


    public void addNews(News news) throws SQLException{
        String dbInsertSql =
                "insert into news " +
                        "(news_title, news_summary, news_source, " +
                        "news_link, news_image_url, user_id, news_upload_date)" +
                        " values (?,?,?,?,?,?,?)";
        try {
            int x = jdbc.update(dbInsertSql,
                    news.getNews_title(),
                    news.getNews_summary(),
                    news.getNews_source(),
                    news.getNews_link(),
                    news.getNews_image_url(),
                    1,
                    LocalDate.now()
            );
        }catch (DataAccessException e) {
            throw new SQLException("Failed to insert new information record", e);
        }
    }





    @Override
    public News getNewsById(int id) throws SQLException {
        String sql = "SELECT * FROM news WHERE news_id = ?";
        try {
            return jdbc.queryForObject(sql, newsMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new SQLException("No news item found with ID: " + id);
        } catch (DataAccessException e) {
            throw new SQLException("Error retrieving news with ID: " + id);
        }
    }

    // method for updating news data from the database
    @Override
    public void updateNews(News news) throws SQLException {

        // sql query that says to update the fields where it match the news id
        String sql = "UPDATE news SET news_title = ?, news_summary = ?, news_source = ?, news_link = ?, " +
                    "news_image_url = ?, user_id = ?, news_upload_date = ? WHERE news_id = ?";
        try {
            // jdbc.update() is a method that will execute the sql query
            // replaces the ? with the actual values from the news object
            int rowsAffected = jdbc.update(sql,
                    news.getNews_title(),
                    news.getNews_summary(),
                    news.getNews_source(),
                    news.getNews_link(),
                    news.getNews_image_url(),
                    news.getUser_id(),
                    news.getNews_upload_date(),
                    news.getNews_id()
            );

            // error handling
            if (rowsAffected == 0) {
                throw new SQLException("No news item was updated. Check the ID provided.");
            }
        } catch (DataAccessException e) {
            throw new SQLException("Error updating news with ID: " + news.getNews_id(), e);
        }
    }

    // method for deleting news data from the database
    @Override
    public void deleteNews(int id) throws SQLException {

        // sql query that says to delete the row where the id matches
        String sql = "DELETE FROM news WHERE news_id = ?";
        try {
            // executing the query with the news id
            int rowsAffected = jdbc.update(sql, id);

            // error handling
            if (rowsAffected == 0) {
                throw new SQLException("No news item was deleted. Check the ID provided.");
            }
        } catch (DataAccessException e) {
            throw new SQLException("Error deleting news with ID: " + id, e);
        }
    }
}
