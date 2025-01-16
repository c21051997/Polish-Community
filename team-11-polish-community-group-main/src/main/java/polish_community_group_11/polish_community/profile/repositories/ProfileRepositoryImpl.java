package polish_community_group_11.polish_community.profile.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.feed.models.FeedImpl;
import polish_community_group_11.polish_community.profile.models.Profile;

@Repository
public class ProfileRepositoryImpl implements ProfileRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Profile> rowMapper;

    public ProfileRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (rs, rowNum) -> new Profile(
                rs.getInt("user_id"),
                rs.getString("fullName"),
                rs.getString("email"),
                rs.getString("profile_picture"),
                rs.getDate("dob").toLocalDate(),
                rs.getString("bio"),
                rs.getString("phone_number"),
                rs.getString("organization"),
                rs.getBoolean("show_phone_number"),
                rs.getBoolean("show_dob")
        );
    }
    @Override
    public void addProfile(Profile profile) {
        String sql = "INSERT INTO user_profile (user_id) VALUES (?)";
        jdbcTemplate.update(sql, profile.getUserId());

    }
    @Override
    public Profile getProfile(Integer id) {
        String sql = "SELECT u.id as user_id, " +
                "u.fullname, u.email, u.dob, u.organization, " +
                "up.profile_picture, up.phone_number, up.bio, up.show_phone_number, up.show_dob " +
                "FROM users u LEFT JOIN user_profile up ON " +
                "u.id = up.user_id WHERE u.id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }


    @Override
    public void updateProfile(Profile profile) {
        String sql = "UPDATE user_profile SET profile_picture = ?," +
                " bio = ?, phone_number = ?, show_phone_number = ?, show_dob = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, profile.getProfilePicture(), profile.getBio(), profile.getPhoneNumber(),profile.isShowPhoneNumber(), profile.isShowDob(), profile.getUserId());
    }
};
