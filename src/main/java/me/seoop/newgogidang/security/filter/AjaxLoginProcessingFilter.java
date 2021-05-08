package me.seoop.newgogidang.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import me.seoop.newgogidang.security.token.AjaxAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        if (!isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        AuthMemberDTO authMemberDTO = objectMapper.readValue(request.getReader(), AuthMemberDTO.class);
        log.info("authMemberDTO: " + authMemberDTO);
        if (StringUtils.isEmpty(authMemberDTO.getEmail()) || StringUtils.isEmpty(authMemberDTO.getPw())) {
            throw new IllegalArgumentException("Email or Password is empty");
        }

        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(authMemberDTO.getEmail(), authMemberDTO.getPassword());
        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest request) {
        log.info(request.getHeader("X-Requested-With"));

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            return true;
        }

        return false;
    }
}
