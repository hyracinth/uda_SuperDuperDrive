package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    private UserService _userService;

    public SignupController(UserService _userService) {
        this._userService = _userService;
    }

    @GetMapping("/signup")
    public String getSignupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignupUser(@ModelAttribute User user, Model model) {
        String signupFailMsg = null;

        // Check if username available
        if(!_userService.isUserAvailable(user.getUsername())) {
            signupFailMsg = "Username not available. Please choose another";
        }

        // Try to add user
        if(signupFailMsg == null) {
            int rowsAdded =_userService.createUser(user);
            if (rowsAdded < 0) {
                signupFailMsg = "There was an error during sign up, please try again.";
            }
        }

        if(signupFailMsg == null) {
            model.addAttribute("signupSuccess", true);
            return "login";
        }
        else {
            model.addAttribute("signupFailMsg", signupFailMsg);
        }

        return "signup";
    }
}
