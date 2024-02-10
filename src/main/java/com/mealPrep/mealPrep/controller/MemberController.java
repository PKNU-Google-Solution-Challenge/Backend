package com.mealPrep.mealPrep.controller;

import com.mealPrep.mealPrep.domain.Enum.UserState;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public String signUp(@Validated MemberForm form, BindingResult result){

        if(result.hasErrors()) {
            return "redirect:/sign-up";
        }

        Member member = new Member();
        member.setMember_id(form.getMember_id());
        member.setPassword(form.getPassword());
        member.setBelong(form.getBelong());
        member.setE_mail(form.getE_mail());
        member.setNickname(form.getNickname());
        member.setRegion(form.getRegion());
        member.setStatus(UserState.activate);

        memberService.join(member);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Validated LoginForm loginForm){

        boolean equals = memberService.equals(loginForm);

        return "redirect:/";
    }

    private class LoginForm {
    }
}
