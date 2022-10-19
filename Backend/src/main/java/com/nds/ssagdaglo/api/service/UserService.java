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

    public boolean signIn(User user) {
        List<User> passwordList = userRepository.findPasswordByUserEmail(user.getUserEmail());

        System.out.println(passwordList.get(0).getUserPassword());
        System.out.println(user.getUserPassword());

        try{
            if(passwordList.get(0) == null) { // email로 조회한 데이터가 없는경우(없는 사용자)
                return false;
            }


            if(!passwordList.get(0).getUserPassword().equals(user.getUserPassword())) {
                // email로 조회한 사용자의 비번과 요청 들어온 비번이 같지 않으면
                return false;
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println(e);
            return false;
        }
        return true; // 위의 2가지가 아닌 경우
    }
}
