package com.mealPrep.mealPrep.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemberForm {

    private String member_id;
    private String password;
    private String nickname;
    private String e_mail;
    private String belong;
    private String region;

}
