package polish_community_group_11.polish_community.comments.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.comments.models.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentJdbcRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CommentJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment save(Comment comment) {
        String sql = "INSERT INTO comment (comment_content, user_id, post_id, created_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                comment.getContent(),
                comment.getUserId(),
                comment.getPostId(),
                comment.getCreatedDate()
        );

        // Retrieve the last inserted ID
        Integer commentId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        comment.setId(commentId);
        return comment;
    }

    @Override
    public List<Comment> findByPostId(int postId) {
        String sql = "SELECT c.id, c.comment_content, c.user_id, c.post_id, c.created_date, " +
                "u.fullname as username, u.email as user_email " +
                "FROM comment c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE c.post_id = ?";
        return jdbcTemplate.query(sql, new CommentRowMapper(), postId);
    }

    private static class CommentRowMapper implements RowMapper<Comment> {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setContent(rs.getString("comment_content"));
            comment.setUserId(rs.getInt("user_id"));
            comment.setPostId(rs.getInt("post_id"));
            comment.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            comment.setUsername(rs.getString("username"));
            comment.setUserEmail(rs.getString("user_email"));
            return comment;
        }
    }
    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM comment WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}