package polish_community_group_11.polish_community.register.services;

import java.util.List;

import polish_community_group_11.polish_community.register.models.User;

public interface UserService {
    void saveUser(User user);

    User findById(int id);

    User findByEmail(String email);
    int findUserIdByEmail(String email);
    
    List<User> findAllUsers();
    public String findUserFullNameByEmail(String email);
}