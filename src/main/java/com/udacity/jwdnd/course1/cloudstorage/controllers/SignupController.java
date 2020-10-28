package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService _userService;

    public SignupController(UserService _userService) {
        this._userService = _userService;
    }

    @GetMapping
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping
    public String postSignupUser(@ModelAttribute User user, Model model) {
        String signupErrorMsg = null;

        // Check if username available
        if(!_userService.isUserAvailable(user.getUsername())) {
            signupErrorMsg = "Username not available. Please choose another";
        }

        // Try to add user
        if(signupErrorMsg == null) {
            int rowsAdded =_userService.createUser(user);
            if (rowsAdded < 0) {
                signupErrorMsg = "There was an error during sign up, please try again.";
            }
        }

        System.out.println(signupErrorMsg);

        if(signupErrorMsg == null) {
            model.addAttribute("signupSuccess", true);
        }
        else {
            model.addAttribute("signupFailMsg", signupErrorMsg);
        }

        return "signup";
    }
}
