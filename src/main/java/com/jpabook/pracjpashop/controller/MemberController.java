package com.jpabook.pracjpashop.controller;

import com.jpabook.pracjpashop.domain.Address;
import com.jpabook.pracjpashop.domain.Member;
import com.jpabook.pracjpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result ){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        // form에서 도시값을 가져와 address에 새롭게 생성성
       Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
        public String list(Model model){
            List<Member> members = memberService.findMembers();
            model.addAttribute("members",members);
            return "members/memberList";

    }
}
