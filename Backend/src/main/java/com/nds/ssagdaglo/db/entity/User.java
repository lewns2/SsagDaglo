package com.nds.ssagdaglo.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@Table
public class User {
//    // 기본 생성자 추가
//    public User(String userEmail, String userNickName, String userPassword) {
//        this.userEmail = userEmail;
//        this.userNickName = userNickName;
//        this.userPassword = userPassword;
//    }

    @Id
    @Email
    @Column(name = "email", length = 100, nullable = false)
    private String userEmail;

    @Column(name = "nickname", length = 60, nullable = false)
    private String userNickName;

    @Column(name = "password", length = 200, nullable = false)
    private String userPassword;

    // 추가 2022-10-24
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.ro
//    }
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @Override
//    public String getUserEmail() {
//        return this.userEmail;
//    }
}


