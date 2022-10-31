package com.nds.ssagdaglo.api.controller;

import com.nds.ssagdaglo.api.dto.FileDto;
import com.nds.ssagdaglo.api.service.UserService;
import com.nds.ssagdaglo.common.ApiResponse;
import com.nds.ssagdaglo.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins="*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ApiResponse<?> signUp(@RequestBody User user) {
        // true이면 가입 성공! / false이면 중복 이메일 존재
        if(userService.signUpUser(user)) {
            return ApiResponse.createSuccessWithNoContent();
        }
        return ApiResponse.createError("회원가입 실패. 중복된 이메일입니다.");
    }

    // login은 Spring Security가 제공하는 것을 사용해서, 기존 코드는 비활성화.
//    @CrossOrigin(origins="*", allowedHeaders = "*")
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String signIn(
//            @RequestBody User user
//    ) {
//        return userService.signIn(user);
//    }

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
