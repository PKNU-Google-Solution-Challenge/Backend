package com.mealPrep.mealPrep.repository;

import com.mealPrep.mealPrep.domain.Comment;
import com.mealPrep.mealPrep.domain.Image;
import com.mealPrep.mealPrep.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

      Member findOneByMemberId(String memberId);
      Member findOneByUserId(Long userId);

//    public void save(Member member) {em.persist(member);}
//
//    public List<Member> findByMemberId(String memberId) {
//        String jpql = "select m from Member m where m.member_id = :memberId";
//        return em.createQuery(jpql, Member.class)
//                .setParameter("memberId", memberId)
//                .getResultList();
//    }
//
//    public Member findOneByMemberId(String memberId) {
//        String jpql = "select m from Member m where m.member_id = :memberId";
//        return em.createQuery(jpql, Member.class)
//                .setParameter("memberId", memberId)
//                .getSingleResult();
//    }
}
