package com.nds.ssagdaglo.db.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_email"}
                )
        }
)

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Email
    @Column(name = "user_email", length = 100, nullable = false)
    String userEmail;

    @Column(name = "user_nickname", length = 60, nullable = false)
    String userNickName;

    @Column(name = "user_password", length = 200, nullable = false)
    String userPassword;

}
