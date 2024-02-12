package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {em.persist(member);}

    public List<Member> findByMemberId(String memberId) {
        String jpql = "select m from Member m where m.member_id = :memberId";
        return em.createQuery(jpql, Member.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public Member findOneByMemberId(String memberId) {
        String jpql = "select m from Member m where m.member_id = :memberId";
        return em.createQuery(jpql, Member.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }

}
