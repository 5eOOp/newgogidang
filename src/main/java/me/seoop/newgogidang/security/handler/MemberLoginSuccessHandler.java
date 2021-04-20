package me.seoop.newgogidang.security.handler;

import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public MemberLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("-----onAuthenticationSuccess-----");
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) authentication.getPrincipal();
        log.info("Authorities: " + authMemberDTO.getAuthorities());
        boolean fromSocial = authMemberDTO.isFromSocial();
        log.info("Need Modify Member? " + fromSocial);
        boolean passwordResult = passwordEncoder.matches("1111", authMemberDTO.getPassword());

        // 소셜 로그인 + 비밀번호 설정 X
        if (fromSocial && passwordResult) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }

        // 로그인시 가게 리스트로 이동
        redirectStrategy.sendRedirect(request, response, "/store/list");
    }
}
