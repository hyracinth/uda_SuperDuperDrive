package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    /*
    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping(value={"/login", "/"})
    public String loginView() {
        return "login";
    }
    @RequestMapping("/login-user")
    public String loginUser(@RequestParam("userName") String username,
                            @RequestParam("password") String password) {
        Authentication result = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(result);
        log.info("LOGIN CONTROLLER");
        Users user = userService.getUser(username);
        authenticatedUserService.setUser(username);
       log.info(authenticatedUserService.getLoggedInUser().toString());
        if (user == null) {
            return "error";
        } else
            return "redirect:/home";
    }
     */

}
