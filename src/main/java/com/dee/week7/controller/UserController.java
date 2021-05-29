package com.dee.week7.controller;

import com.dee.week7.entity.Post;
import com.dee.week7.entity.User;
import com.dee.week7.repository.UserRepository;
import com.dee.week7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private HttpSession httpSession;

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-account")
    public String createAccount(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newAccount";
    }

    @PostMapping("/save-user")
    public String saveUser(User user, Model model) {

        userService.addNewUser(user);
        //System.out.println(user.toString());
        return "index";
    }

    @PostMapping("/login")
    public String userLogin(User user, Model model, HttpServletRequest request){


        User userFromDB = userService.getUserFromDB(user);
        boolean valid = userService.checkForUser(user);
        if (valid){
            Post post = new Post();
            model.addAttribute("post", post);

            String name = userFromDB.getFirstName()+" "+userFromDB.getLastName();

            model.addAttribute("username", name);
            httpSession.setAttribute("userID", userFromDB.getUserId());
            httpSession.setAttribute("userEMAIL", userFromDB.getEmail());

            httpSession.getAttribute("userID");

            request.setAttribute(
                    View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
            return "redirect:/save-post";
        }
        model.addAttribute("message", "Invalid email or password");
        return "index";
    }

//    @GetMapping("/showposts")
//    public String showPosts(Model model){
//        return "timeline";
//    }
    @GetMapping("/logout")
    public String userLogout(Model model){
        httpSession.invalidate();
        return "index";
    }
}
