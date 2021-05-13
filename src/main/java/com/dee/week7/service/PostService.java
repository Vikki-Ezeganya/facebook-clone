package com.dee.week7.service;

import com.dee.week7.entity.Comment;
import com.dee.week7.entity.Post;
import com.dee.week7.entity.User;
import com.dee.week7.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(Post post, User user){
        postRepository.save(post);
        user.addPostToList(post);
    }

    public void createComment(Comment comment, User user){
        //postRepository.save(comment);
        //user.addPostToList(post);
    }


    //@Query(value = "SELECT * FROM post p WHERE u.user_id = ?1", nativeQuery = true)
    public List<Post> fetchPost(User user){
        //return postRepository.findAllByUserID(userID);
        return postRepository.findPostByUserOrderByDateDesc(user);
        //return postRepository.getAllByUserUserId(userID);
        //return null;
    }

    @Transactional
    public void deletePost(Long postID){
//        postRepository.delete(post);
        postRepository.deleteById(postID);
    }
    public Post findPostById(Long postID){
        return postRepository.getPostByPostId(postID);
    }

    @Transactional
    public void update(Long post_id, String edited){
        Optional<Post> post = postRepository.findById(post_id);
        post.get().setBody(edited);
        post.ifPresent(post1 -> post1.setBody(edited));
    }
    @Transactional
    public void updateLikes(Long post_id, int likes){
        Optional<Post> post = postRepository.findById(post_id);
        post.get().setNumofLikes(likes);
        //post.ifPresent(post1 -> post1.setBody(edited));
    }


}
