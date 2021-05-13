package com.dee.week7.repository;

import com.dee.week7.entity.Post;
import com.dee.week7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    //User findByUserID(Long userID);
    User findUserByEmail(String email);

    //    @Query("SELECT u.firstName, p.postID, p.message, p.timestamp, p.totalLikes From User u JOIN u.posts p")
//    List<Post> getUserPostsById(Long userID);
}
