package com.dee.week7.repository;

import com.dee.week7.entity.Comment;
import com.dee.week7.entity.Post;
import com.dee.week7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    List<Post> findAll();
    Post getPostByPostId(Long postID);
    List<Post> findPostByUserOrderByDateDesc(User user);

}
