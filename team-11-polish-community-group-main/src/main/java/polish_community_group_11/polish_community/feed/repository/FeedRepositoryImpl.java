package polish_community_group_11.polish_community.feed.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.feed.models.Feed;
import polish_community_group_11.polish_community.feed.models.FeedImpl;

import java.util.List;

@Repository
public class FeedRepositoryImpl implements FeedRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<FeedImpl> feedMapper;

    public FeedRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.feedMapper = (rs, rowNum) -> {
            FeedImpl feed = new FeedImpl();
            feed.setPostId(rs.getInt("post_id"));
            feed.setPostImageUrl(rs.getString("post_image_url"));
            feed.setPostTitle(rs.getString("post_title"));
            feed.setPostDescription(rs.getString("post_description"));
            feed.setPostTime(rs.getDate("post_time").toLocalDate());
            feed.setUserId(rs.getInt("user_id"));
            feed.setAuthorName(rs.getString("fullname"));
            feed.setAuthorOrganization(rs.getString("organization"));
            feed.setTags(getTagsForPost(rs.getInt("post_id")));
            feed.setLikesCount(getLikesCount(rs.getInt("post_id")));
            return feed;
        };
    }

    // getting all posts
    @Override
    public List<FeedImpl> getAllPosts() {
        String sql = "SELECT f.*, u.fullname, u.organization " +
                "FROM feed f " +
                "LEFT JOIN users u ON f.user_id = u.id " +
                "ORDER BY f.post_time DESC";
        return jdbcTemplate.query(sql, feedMapper);
    }

    // getting a post by id
    @Override
    public FeedImpl getPostById(int postId) {
        String sql = "SELECT f.*, u.fullname, u.organization " +
                "FROM feed f " +
                "LEFT JOIN users u ON f.user_id = u.id " +
                "WHERE f.post_id = ?";
        return jdbcTemplate.queryForObject(sql, feedMapper, postId);
    }

    // adding a new post
    @Override
    public void addNewFeedPost(FeedImpl feed) {
        String sql = "INSERT INTO feed (post_image_url, post_title, post_description, post_time, user_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                feed.getPostImageUrl() != null ? feed.getPostImageUrl() : "",
                feed.getPostTitle(),
                feed.getPostDescription(),
                java.sql.Date.valueOf(feed.getPostTime()),
                feed.getUserId()
        );

        int postId = getGeneratedPostId();

        List<String> tags = feed.getTags();
        if (tags != null && !tags.isEmpty()) {
            insertTagsForPost(postId, tags);
        }
    }


    // update a post that is editing
    @Override
    public void updatePost(int postId, FeedImpl feed) {
        String sql = "UPDATE feed SET post_title = ?, post_description = ?, post_image_url = ? WHERE post_id = ?";

        jdbcTemplate.update(sql,
                feed.getPostTitle(),
                feed.getPostDescription(),
                feed.getPostImageUrl(),
                postId
        );

        // update the tags
        jdbcTemplate.update("DELETE FROM feed_tags WHERE post_id = ?", postId);
        if (feed.getTags() != null && !feed.getTags().isEmpty()) {
            insertTagsForPost(postId, feed.getTags());
        }
    }

    // deleting a post
    @Override
    public void deletePost(int postId) {
        // delete associated likes, tags then the post
        jdbcTemplate.update("DELETE FROM post_likes WHERE post_id = ?", postId);

        jdbcTemplate.update("DELETE FROM feed_tags WHERE post_id = ?", postId);

        jdbcTemplate.update("DELETE FROM feed WHERE post_id = ?", postId);
    }

    // liking a post
    @Override
    public void likePost(int postId, int userId) {
        String sql = "INSERT INTO post_likes (post_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, postId, userId);
    }

    // removing like
    @Override
    public void unlikePost(int postId, int userId) {
        String sql = "DELETE FROM post_likes WHERE post_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, postId, userId);
    }

    // checking if user had liked or not
    @Override
    public boolean hasUserLikedPost(int postId, int userId) {
        String sql = "SELECT COUNT(*) FROM post_likes WHERE post_id = ? AND user_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, postId, userId);
        return count > 0;
    }

    // getting number of likes on post
    private int getLikesCount(int postId) {
        String sql = "SELECT COUNT(*) FROM post_likes WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }


    // getting tags associated with post
    private List<String> getTagsForPost(int postId) {
        String sql = "SELECT t.tag_name FROM tags t " +
                "INNER JOIN feed_tags ft ON t.tag_id = ft.tag_id " +
                "WHERE ft.post_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("tag_name"), postId);
    }

    // getting id of the recently created post
    private int getGeneratedPostId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }


    private void insertTagsForPost(int postId, List<String> tags) {
        for (String tag : tags) {
            // check for empty tags
            if (tag == null || tag.trim().isEmpty()) {
                continue;
            }

            String cleanTag = tag.trim().toLowerCase();
            int tagId = insertTagAndGetId(cleanTag);
            // link to post
            try {
                jdbcTemplate.update(
                        "INSERT INTO feed_tags (post_id, tag_id) VALUES (?, ?)",
                        postId, tagId
                );
            } catch (Exception e) {
                // handle duplicate tag associations silently
                if (!e.getMessage().contains("duplicate")) {
                    throw e;
                }
            }
        }
    }


    private int insertTagAndGetId(String tag) {
        try {
            String checkSql = "SELECT tag_id FROM tags WHERE tag_name = ?";
            Integer tagId = jdbcTemplate.queryForObject(checkSql, new Object[]{tag}, Integer.class);
            return tagId != null ? tagId : createNewTag(tag);
        } catch (Exception e) {
            return createNewTag(tag);
        }
    }

    private int createNewTag(String tag) {
        jdbcTemplate.update("INSERT INTO tags (tag_name) VALUES (?)", tag);
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }
}