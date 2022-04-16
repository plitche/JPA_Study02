package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@WebAppConfiguration
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }
    
    @Test
    //@Test (expected = IllegalStateException.class) ??
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setUsername("kim1");

        Member member2 = new Member();
        member2.setUsername("kim1");

        //when
        memberService.join(member1);
        try {
            memberService.join(member2); // 예외가 발생해야 한다!!!
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야 한다.");
    }    
}