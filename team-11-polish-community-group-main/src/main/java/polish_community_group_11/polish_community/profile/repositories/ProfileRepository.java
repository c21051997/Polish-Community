package polish_community_group_11.polish_community.profile.repositories;

import org.springframework.stereotype.Repository;
import polish_community_group_11.polish_community.profile.models.Profile;

import java.util.List;

@Repository
public interface ProfileRepository {
  Profile getProfile(Integer id);
  void addProfile(Profile profile);
  void updateProfile(Profile profile);
}
