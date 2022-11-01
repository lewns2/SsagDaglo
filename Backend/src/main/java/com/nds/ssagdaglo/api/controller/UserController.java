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
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse<?> signIn(@RequestBody User user) {

        String res = userService.signIn(user);
        if(res.equals("false")) {
            return ApiResponse.createError("로그인 오류");
        }
        return ApiResponse.createSuccess(res);
    }

    // 닉네임 중복 확인 함수
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping(value = "/chknickname")
    public ApiResponse<?> checkNickName(String userNickName) throws IOException {
        Boolean isPossible;
        isPossible = userService.checkNickName(userNickName);

        return (isPossible ? ApiResponse.createSuccessWithNoContent() : ApiResponse.createError("중복된 닉네임이 존재합니다."));
    }
    @CrossOrigin(origins="*", allowedHeaders = "*")
    @PostMapping(value = "/chkemail")
    public ApiResponse<?> checkEmail(String userEmail) throws IOException {
        Boolean isPossible;
        isPossible = userService.checkEmail(userEmail);

        return (isPossible ? ApiResponse.createSuccessWithNoContent() : ApiResponse.createError("중복된 이메일이 존재합니다."));
    }
}
