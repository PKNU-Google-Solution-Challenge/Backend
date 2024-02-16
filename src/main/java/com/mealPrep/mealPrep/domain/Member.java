package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.UserState;
import com.mealPrep.mealPrep.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    private String memberId;
    private String password;
    private String nickname;
    private String e_mail;
    private String belong;
    private String region;

    @Column(columnDefinition = "integer default 0")
    private Long reward;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.EAGER)
    private List<Follow> followings;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.EAGER)
    private List<Follow> followers;

    @Enumerated(EnumType.STRING)
    private UserState status;
}
