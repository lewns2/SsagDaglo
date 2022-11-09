package com.nds.ssagdaglo.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
@Data // Getter + Setter
@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@Table
public class User {

    @Id
    @Email
    @Column(name = "email", length = 100, nullable = false)
    private String userEmail;

    @Column(name = "nickname", length = 60, nullable = false)
    private String userNickName;

    @Column(name = "password", length = 200, nullable = false)
    private String userPassword;

    private String roles; // USER, ADMIN

    public List<String> getRoleList() {
        if(this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}


