package com.nds.ssagdaglo.api.controller;

import com.nds.ssagdaglo.api.service.UserService;
import com.nds.ssagdaglo.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public boolean signUp(
            @RequestBody User user
    ) {
        userService.signUpUser(user);
//        return user;
        return true;
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(
            @RequestBody User user
    ) {
        return userService.signIn(user);
    }

    @PostMapping(value = "/chknickname")
    public boolean checkNickName(
            @RequestBody User user
    ) {
        return userService.checkNickName(user);
    }

    @RequestMapping(value = "/chkemail", method = RequestMethod.POST)
    public boolean checkEmail(
            @RequestBody User user
    ) {
        return userService.checkEmail(user);
    }
}
