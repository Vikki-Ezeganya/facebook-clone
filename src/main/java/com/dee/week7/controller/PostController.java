package com.dee.week7.controller;

import com.dee.week7.entity.Comment;
import com.dee.week7.entity.Post;
import com.dee.week7.entity.User;
import com.dee.week7.service.CommentService;
import com.dee.week7.service.PostService;
import com.dee.week7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/save-post")
    public String savePost(Post post, Model model) {

        Object userEMAIL = httpSession.getAttribute("userEMAIL");
        Object userID = httpSession.getAttribute("userID");

        System.out.println("USER EMAIL IS NOWWWW :=" + userEMAIL);
        System.out.println("USER ID ISSSSS NOWWWW :=" + userID);
        User userByMail = userService.findUserByMail((String) userEMAIL);
        String username = userByMail.getFirstName() + " " + userByMail.getLastName();

        post.setUser(userByMail);
        userByMail.addPostToList(post);


        if (post.getBody() != null && !post.getBody().isBlank()) {
            postService.createPost(post, userByMail);

            List<Post> postList = postService.fetchPost(userByMail);


            model.addAttribute("posts", postList);
            model.addAttribute("username", username);

            System.out.println(post.getBody());
            return "redirect:/gototimeline";
        }
        return "redirect:/gototimeline";
    }

    @GetMapping("/delete")
    public String deletePost(@RequestParam(value = "postID") Long postID, Model model) {
        System.out.println("THE POST IDDDDDDD ISSSSSSS :==="+postID);

        postService.deletePost(postID);

        return "redirect:/gototimeline";
    }

    @PostMapping("/edit-post")
    public String editPost(@RequestParam(value = "postbody") String postbody,
                           @RequestParam(value = "post_id") Long post_id,
                           Model model){
        System.out.println(postbody);
        System.out.println(post_id);
        Post postById = postService.findPostById(post_id);
        model.addAttribute("post", postById);
        model.addAttribute("postToEdit", postbody);
        model.addAttribute("post_id", post_id);

        return "editThePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@RequestParam(value = "postID") Long postID,
                             @RequestParam(value = "postbody") String s,
                             Model model,
                             Post post){
        System.out.println("THE POST BODY ISSSSSSSS:======"+post.getBody());
        postService.update(postID, post.getBody());
        return "redirect:/gototimeline";
    }


    @GetMapping("/gototimeline")
    public String gotoTimeline(Post post,
                               Model model
                               ){

        Object userEMAIL = httpSession.getAttribute("userEMAIL");
        Object userID = httpSession.getAttribute("userID");

        System.out.println("USERRRRRRRIIIIIIDDDDD "+userID);

        User userByMail = userService.findUserByMail((String) userEMAIL);

        List<Post> postList = postService.fetchPost(userByMail);

        String username = userByMail.getFirstName() + " " + userByMail.getLastName();

        model.addAttribute("posts", postList);
        model.addAttribute("username", username);



        return "timeline";
    }

//    @Value("mymessage")
    @PostMapping("/addComment")
    public String addComment(@Value("mymessage") @RequestParam(value = "post_id") Long post_id,
                             Comment comment,
                             Model model,
                             RedirectAttributes redirectAttributes){

        Post postById = postService.findPostById(post_id);
        //System.out.println("THE COMMENT POST ID ISSSSSS:===="+ post_id);

        if (comment.getComment_body()!=null){
            comment.setPost(postById);
            postById.addComment(comment);
            commentService.addComment(comment);


            return "redirect:/gototimeline";
        }
        return "redirect:/gototimeline";

    }

    @PostMapping("/addLike")
    public String like(@RequestParam(value = "post_id") Long post_id,Model model){
        Post postById = postService.findPostById(post_id);
        int i = postById.getNumofLikes() + 1;
        postService.updateLikes(post_id,i);
        return "redirect:/gototimeline";
    }


    @ModelAttribute("comment")
    public Comment message(){
        Comment comment = new Comment();
        return comment;
    }

    @ModelAttribute("post")
    public Post messages(){
        Post post = new Post();
        return post;
    }


}
