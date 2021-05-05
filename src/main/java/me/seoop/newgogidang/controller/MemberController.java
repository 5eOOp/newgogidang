package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import me.seoop.newgogidang.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerPost(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("memberDTO: " + memberDTO);
        memberDTO.setPw(passwordEncoder.encode(memberDTO.getPw()));
        Long mid = memberService.register(memberDTO);
        redirectAttributes.addFlashAttribute("msg", mid);
        return "redirect:/member/login";
    }

    @GetMapping("/loginPage")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/member/login")
    public String loginPost(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        
        return "redirect:/store/list";
    }

    @GetMapping("/member/logout")
    public void  logout() {

    }

    @GetMapping("/member/read")
    public String getMember(Long mid, Model model) {
        log.info("mid: " + mid);
        MemberDTO memberDTO = memberService.getMember(mid);
        model.addAttribute("dto", memberDTO);

        return "member/read";
    }

    @GetMapping("/member/modify")
    public String modifyForm(Long mid, Model model) {
        log.info("modify mid: " + mid);
        MemberDTO memberDTO = memberService.getMember(mid);
        model.addAttribute("dto", memberDTO);
        return "member/modify";
    }

    @PostMapping("/member/modify")
    public String modify(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("post modify.....");
        log.info("dto: " + memberDTO);
        memberService.modify(memberDTO);
        redirectAttributes.addAttribute("mid",memberDTO.getMid());

        return "redirect:/member/read";
    }

    @PostMapping("/member/remove")
    public String remove(Long mid, RedirectAttributes redirectAttributes) {
        log.info("remove mid: " + mid);
        memberService.remove(mid);
        redirectAttributes.addFlashAttribute("msg", mid);

        return "redirect:/store/list";
    }
}
