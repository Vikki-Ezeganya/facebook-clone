package com.dee.week7.repository;

import com.dee.week7.entity.Comment;
import com.dee.week7.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    //@Query( value = "select comment_body from comments where post_id=27", nativeQuery = true)
    List<Comment> findCommentByPost(Post post);
//    List<Comment> getCommentByPostPostId()
}
