package com.nds.ssagdaglo.api.service;

import com.nds.ssagdaglo.db.entity.User;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void signUpUser(User user) {
//        user.setUserEmail();
        userRepository.save(user);
    }

    public String signIn(User user) {
        List<User> passwordList = userRepository.findPasswordByUserEmail(user.getUserEmail());

//        System.out.println(passwordList.get(0).getUserPassword());
//        System.out.println(user.getUserPassword());

        try{
            if(passwordList.size() != 1) { // email로 조회한 데이터가 없는경우(없는 사용자)
                System.out.println("[LOG] 로그인 실패! (존재하지 않는 이메일) - " + user.getUserEmail());
                return "false";
            }


            if(!passwordList.get(0).getUserPassword().equals(user.getUserPassword())) {
                // email로 조회한 사용자의 비번과 요청 들어온 비번이 같지 않으면
                System.out.println("[LOG] 로그인 실패! (비밀번호가 일치하지 않음) - " + user.getUserEmail());
                return "false";
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return "false";
        }
        System.out.println("[LOG] 로그인 성공! - " + user.getUserEmail());
        return userRepository.findNiknameByUserEmail(user.getUserEmail()).get(0).getUserNickName(); // 위의 2가지가 아닌 경우
    }
    
    public boolean checkNickName(User user) {
        // 닉네임 중복 체크
//        System.out.println("list에 저장된 값: " + user.toString());
        List<User> nickNameList = userRepository.findAllByUserNickName(user.getUserNickName());
//        System.out.println(nickNameList.size()); // size가 1인 경우 중복
//        nickNameList.get(0).getUserNickName()
        if(nickNameList.size() >= 1) {
            // 중복이면 false
            System.out.println("[LOG] 닉네임 중복! - " + user.getUserNickName());
            return false;
        }

        else {
            System.out.println("[LOG] 닉네임 중복아님! - " + user.getUserNickName());
            return true; // 중복이 아니면 true
        }

    }

    public boolean checkEmail(User user) {
//        System.out.println("list에 저장된 값: " + user.toString());

        // 이메일 중복 체크
        List<User> emailList = userRepository.findAllByUserEmail(user.getUserEmail());
//        System.out.println(emailList.size()); // size가 1인 경우 중복

        if(emailList.size() >= 1) {
            System.out.println("[LOG] 이메일 중복! - " + user.getUserEmail());
            return false;// 중복이면 false
        }
        else {
            System.out.println("[LOG] 이메일 중복아님! - " + user.getUserEmail());
            return true; // 중복이 아니면 true
        }

    }
}
