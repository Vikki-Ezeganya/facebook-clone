package com.dee.week7.service;

import com.dee.week7.entity.Comment;
import com.dee.week7.entity.Post;
import com.dee.week7.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(Comment comment){
        commentRepository.save(comment);
    }
//
//    public List<Comment> getComment(Post post){
//         return commentRepository.findCommentByPost(post);
////         return commentRepository.findAll();
//    }
}
