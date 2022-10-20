package com.nds.ssagdaglo.api.request;

import com.nds.ssagdaglo.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserSignUpRequest {
    private String authId;

    private String password;

    public static User toEntity(UserSignUpRequest body) {
        return User.builder()
                .userEmail(body.authId)
                .userPassword(body.getPassword())
                .build();
    }
}
