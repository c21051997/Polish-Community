package polish_community_group_11.polish_community.register.dao;

import java.util.List;

import polish_community_group_11.polish_community.register.models.User;

public interface UserRepository {
    int saveUser(User user); // add user into the database

    User findByEmail(String email); // find user by email
    int findUserIdByEmail(String email);

    User findById(int id); // find user by ID

    List<User> findAllUsers(); // get all the users

    String findUserFullNameByEmail(String email);
}