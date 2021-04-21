package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import me.seoop.newgogidang.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerPost(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("memberDTO: " + memberDTO);
        Long mid = memberService.register(memberDTO);
        redirectAttributes.addFlashAttribute("msg", mid);
        return "redirect:/member/login";
    }

    @GetMapping("/")
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
}
