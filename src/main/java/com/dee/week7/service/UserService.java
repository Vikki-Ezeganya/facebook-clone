package com.dee.week7.service;

import com.dee.week7.entity.Post;
import com.dee.week7.entity.User;
import com.dee.week7.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private HttpSession httpSession;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void addNewUser(User user) {
        User userByEmail = userRepository.findUserByEmail(user.getEmail());
        if (userByEmail!=null){
            throw new IllegalStateException("Email taken");
        }
        userRepository.save(user);
    }

    public boolean checkForUser(User user){

        User userByEmail = userRepository.findUserByEmail(user.getEmail());
        boolean present = Optional.ofNullable(userByEmail).isPresent();
        if (present && user.getPassword().equals(userByEmail.getPassword())){
            httpSession.setAttribute("userID", userByEmail.getUserId());
            return true;
        }
        return false;
    }
    public User getUserFromDB(User user){
        return userRepository.findUserByEmail(user.getEmail());
    }

    public User findUserByMail(String email){
        return userRepository.findUserByEmail(email);
    }

    public List<Post> getUserPosts(User user){
        User userByEmail = userRepository.findUserByEmail(user.getEmail());
        return userByEmail.getPosts();
        //return null;
    }

}
