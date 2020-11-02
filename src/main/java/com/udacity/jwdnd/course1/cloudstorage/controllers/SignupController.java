package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This controller handles logic regarding signup
 */
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

    /**
     * This method handles user sign up.
     * If the username exists, it will display an error
     * Otherwise, it will try to add the user.
     * On error, it will display an error.
     * On success, it will redirect to the login page and display a success message
     * @param user user to be added
     * @param model Model object for data binding
     */
    @PostMapping("/signup")
    public String postSignupUser(@ModelAttribute User user, Model model) {
        String signupFailMsg = null;

        if(!_userService.isUserAvailable(user.getUsername())) {
            signupFailMsg = "Username not available. Please choose another";
        }

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
