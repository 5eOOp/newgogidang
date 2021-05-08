package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home";
    }

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
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception, Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "member/login";
    }

    @PostMapping("/login")
    public String loginPost(HttpSession session) {
        
        return "redirect:/store/list";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }

    @GetMapping(value="/denied")
    public String accessDenied(@RequestParam(value = "exception", required = false) String exception, Model model) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal();
        model.addAttribute("email", member.getEmail());
        model.addAttribute("exception", exception);

        return "member/denied";
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
