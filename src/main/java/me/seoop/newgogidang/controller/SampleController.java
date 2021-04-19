package me.seoop.newgogidang.controller;

import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/sample/")
public class SampleController {

    @GetMapping("/all")
    public void exAll() {
        log.info("exAll....");
    }

    @GetMapping("/member")
    public void exAMember(@AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        log.info("exMember....");

        log.info("-----member------");
        log.info("authMemberDTO: " + authMemberDTO);
    }

    @GetMapping("/admin")
    public void exAdmin() {
        log.info("exAdmin....");
    }
}
