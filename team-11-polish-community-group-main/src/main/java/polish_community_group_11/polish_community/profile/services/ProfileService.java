package polish_community_group_11.polish_community.profile.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.profile.models.Profile;
import polish_community_group_11.polish_community.profile.repositories.ProfileRepository;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;
    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
    public void addProfile(Profile profile) {profileRepository.addProfile(profile);}
    public Profile getProfile(Integer userId) {
        return profileRepository.getProfile(userId);
    }
    public void updateProfile(Profile profile) {
        profileRepository.updateProfile(profile);
    }
}
