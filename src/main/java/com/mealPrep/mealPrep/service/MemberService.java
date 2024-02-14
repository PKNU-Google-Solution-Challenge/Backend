package com.mealPrep.mealPrep.service;

import com.mealPrep.mealPrep.controller.LoginForm;
import com.mealPrep.mealPrep.controller.MemberForm;
import com.mealPrep.mealPrep.domain.Enum.UserState;
import com.mealPrep.mealPrep.domain.Member;
import com.mealPrep.mealPrep.exception.BusinessLogicException;
import com.mealPrep.mealPrep.exception.ExceptionCode;
import com.mealPrep.mealPrep.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return ResponseEntity.ok("join successful");
    }

    private ResponseEntity validateDuplicateMember(Member member) {
        List<Member> findMembers = (List<Member>) memberRepository.findOneByMemberId(member.getMember_id());
        if (!findMembers.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_MEMBER_EXIST);
        }
        return ResponseEntity.ok("Duplication successful");
    }


    @Transactional
    public Long login(LoginForm loginForm) {
        Member member = memberRepository.findOneByMemberId(loginForm.getMember_id());

         if (member == null || !member.getPassword().equals(loginForm.getPassword())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_MATCH);
        }

        // 로그인 성공에 대한 로직 추가 예정

        return member.getUser_id();
    }

    @Transactional
    public ResponseEntity editMember(MemberForm form, Long userId){
        Member findMember = memberRepository.findOneByUserId(userId);
        findMember.setBelong(form.getBelong());
        findMember.setRegion(form.getRegion());
        findMember.setNickname(form.getNickname());
        findMember.setE_mail(form.getE_mail());
        return ResponseEntity.ok("Update successful");
    }

    public ResponseEntity suspendMember(Long userId) {
        Member findMember = memberRepository.findOneByUserId(userId);
        findMember.setStatus(UserState.deactivate);
        return ResponseEntity.ok("Stop this account");
    }
}
