package com.nds.ssagdaglo.api.dto;

import com.nds.ssagdaglo.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@Builder
@ToString
public class UserDto {
    private String authId;
    private String password;
    private String nickName;

    public static User toEntity(UserDto body) {
        return User.builder()
                .userEmail(body.authId)
                .userPassword(body.getPassword())
                .userNickName(body.getNickName())
                .build();
    }

    public UserDto(String email, String password) {
//        this.authId = email;
    }
}
