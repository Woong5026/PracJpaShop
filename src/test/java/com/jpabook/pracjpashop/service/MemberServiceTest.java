package com.jpabook.pracjpashop.service;

import com.jpabook.pracjpashop.domain.Member;
import com.jpabook.pracjpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    @Rollback(false)
    public void 회원가입() throws Exception{

        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }
    @Test(expected = IllegalStateException.class)
    public void 중복회원테스트() throws Exception{
        // given
        Member member = new Member();
        member.setName("lee");

        Member member2 = new Member();
        member2.setName("lee");
        // when

        memberService.join(member);
        memberService.join(member2);

        // then
        fail("중복 예외 발생");
    }


}