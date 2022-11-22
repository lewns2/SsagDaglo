package com.nds.ssagdaglo.api.service;

import com.nds.ssagdaglo.db.entity.User;
import com.nds.ssagdaglo.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import javax.jws.soap.SOAPBinding;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public boolean signUpUser(User user) {
        // 이메일 체크해서 중복 확인
        if(userRepository.findById(user.getEmail()).isPresent())
            return false;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return true;
    }

    // 로그인은 Spring Security에서 제공하는 것을 사용하므로, 주석처리
    public String signIn(User user) {
        List<User> passwordList = userRepository.findPasswordByUserEmail(user.getEmail());

        try{
            if(passwordList.size() != 1) { // email로 조회한 데이터가 없는경우(없는 사용자)
                System.out.println("[LOG] 로그인 실패! (존재하지 않는 이메일) - " + user.getEmail());
                return "false";
            }

            if(!bCryptPasswordEncoder.matches(user.getPassword(), passwordList.get(0).getPassword())) {
                // email로 조회한 사용자의 비번과 요청 들어온 비번이 같지 않으면
                System.out.println("[LOG] 로그인 실패! (비밀번호가 일치하지 않음) - " + user.getEmail());
                System.out.println("[LOG] 로그인 실패! (비밀번호가 일치하지 않음) - " + passwordList.get(0).getPassword());
                System.out.println("[LOG] 로그인 실패! (비밀번호가 일치하지 않음) - " + bCryptPasswordEncoder.matches(user.getPassword(), passwordList.get(0).getPassword()));
                return "false";
            }

        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return "false";
        }
        System.out.println("[LOG] 로그인 성공! - " + user.getEmail());
        return userRepository.findNicknameByUserEmail(user.getEmail()).get(0).getNickName(); // 위의 2가지가 아닌 경우
    }


    public boolean checkNickName(String userNickName) {
        return true;
    }

    public boolean checkEmail(String userEmail) {
        return true;
    }
}
