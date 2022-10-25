package com.nds.ssagdaglo.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"email"}
                )
        }
)

@Setter
@Getter
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@Table
public class User {
    @Id
    @Email
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email", length = 100, nullable = false)
    private String userEmail;

    @Column(name = "nickname", length = 60, nullable = false)
    private String userNickName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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


