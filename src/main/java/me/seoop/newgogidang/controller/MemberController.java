package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String registerPost(MemberDTO memberDTO, RedirectAttributes redirectAttributes) {
        log.info("memberDTO: " + memberDTO);
        Long mid = memberService.register(memberDTO);
        redirectAttributes.addFlashAttribute("msg", mid);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String loginPost(MemberDTO memberDTO) {
        log.info("memberDTO: " + memberDTO);
        int count = memberService.login(memberDTO);
        if (count == 1) {
            return "redirect:/store/list";
        } else {
            return "redirect:/member/login";
        }
    }
}
