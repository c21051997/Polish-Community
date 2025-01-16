package polish_community_group_11.polish_community.register.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polish_community_group_11.polish_community.register.models.User;
import polish_community_group_11.polish_community.register.dao.UserRepository;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    // @Autowired has spring automatically create an instance of UserRepository
    @Autowired
    private UserRepository userRepository;

    // @Override marks this method as an implementation of the saveUser method from UserService
    @Override
    public void saveUser(User user) {
        // calls saveUser method
        userRepository.saveUser(user);
    }

    @Override
    public User findById(int id) {
        // calls findById method
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        // calls findByEmail method
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        // Calls findAll method of the UserRepository
        return userRepository.findAllUsers();
    }
    public int findUserIdByEmail(String email){
        return userRepository.findUserIdByEmail(email);
    }
    public String findUserFullNameByEmail(String email){
        return userRepository.findUserFullNameByEmail(email);
    }
}
