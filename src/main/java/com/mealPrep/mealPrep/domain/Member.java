package com.mealPrep.mealPrep.domain;

import com.mealPrep.mealPrep.domain.Enum.UserState;
import com.mealPrep.mealPrep.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    private String member_id;
    private String password;
    private String nickname;
    private String e_mail;
    private String belong;
    private String region;

    @Column(columnDefinition = "integer default 0")
    private Long reward;

    @OneToMany
    @JoinColumn(name = "board_id")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<Follow> followings;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<Follow> followers;

    @Enumerated(EnumType.STRING)
    private UserState status;
}
