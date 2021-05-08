package me.seoop.newgogidang.security.provider;

import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.security.common.FormWebAuthenticationDetails;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import me.seoop.newgogidang.security.service.MemberUserDetailsService;
import me.seoop.newgogidang.security.token.AjaxAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberUserDetailsService memberUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        AuthMemberDTO authMemberDTO = (AuthMemberDTO) memberUserDetailsService.loadUserByUsername(username);
        log.info("username: " + username);
        log.info("password: " + password);
        log.info("authMemberDTO: " + authMemberDTO);

//        if (!passwordEncoder.matches(password, authMemberDTO.getPassword())) {
//            throw new BadCredentialsException("BadCredentialsException");
//        }

        return new AjaxAuthenticationToken(authMemberDTO.getEmail(), null, authMemberDTO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
