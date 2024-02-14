package com.mealPrep.mealPrep.controller;

import com.mealPrep.mealPrep.domain.Enum.UserState;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.exception.BusinessLogicException;
import com.mealPrep.mealPrep.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@Validated @RequestBody MemberForm form){

        Member member = new Member();
        member.setMember_id(form.getMember_id());
        member.setPassword(form.getPassword());
        member.setBelong(form.getBelong());
        member.setE_mail(form.getE_mail());
        member.setNickname(form.getNickname());
        member.setRegion(form.getRegion());
        member.setStatus(UserState.activate);

        try {
            ResponseEntity response = memberService.join(member);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BusinessLogicException e) {
            return new ResponseEntity<>(e.getExceptionCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginForm loginForm) {
        try {
            Long loginUserId = memberService.login(loginForm);
            return new ResponseEntity<>(loginUserId, HttpStatus.OK);
        } catch (BusinessLogicException e) {
            return new ResponseEntity<>(e.getExceptionCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-member/{memberId}")
    public ResponseEntity editMember(@Validated @RequestBody MemberForm form, @PathVariable Long userId){
        ResponseEntity response = memberService.editMember(form, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/suspension/{memberId}")
    public ResponseEntity suspendMember(@Validated @PathVariable Long userId){
        ResponseEntity response = memberService.suspendMember(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
