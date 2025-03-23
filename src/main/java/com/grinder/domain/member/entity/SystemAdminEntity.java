package com.grinder.domain.member.entity;

import com.grinder.common.entity.BaseDateEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.grinder.domain.member.model.SystemAdmin;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class SystemAdminEntity extends BaseDateEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    public SystemAdmin toSystemAdmin() {
        return SystemAdmin.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .build();
    }

    public void update(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
