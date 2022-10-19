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
    public String signUp(
            @RequestBody User user
    ) {
        userService.signUpUser(user);
//        return user;
        return "SignUp!";
    }

    @CrossOrigin(origins="*", allowedHeaders = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean signIn(
            @RequestBody User user
    ) {
        if(userService.signIn(user))
            return true;
        else
            return false;
    }
}
