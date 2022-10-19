package com.nds.ssagdaglo.db.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

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
public class User {
    @Id
    @Email
    @Column(name = "email", length = 100, nullable = false)
    String userEmail;

    @Column(name = "nickname", length = 60, nullable = false)
    String userNickName;

    @Column(name = "password", length = 200, nullable = false)
    String userPassword;

}


