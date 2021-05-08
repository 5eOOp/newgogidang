package me.seoop.newgogidang.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
public class AuthMemberDTO extends User implements OAuth2User {

    private String email;
    private String pw;
    private String nickname;
    private boolean fromSocial;
    private Map<String, Object> attr;

    public AuthMemberDTO(String username,
                         String pw,
                         boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities,
                         Map<String, Object> attr) {
        this(username, pw, fromSocial, authorities);
        this.attr = attr;
    }

    public AuthMemberDTO(String username,
                         String pw,
                         boolean fromSocial,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, pw, authorities);
        this.email = username;
        this.pw = pw;
        this.fromSocial = fromSocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return this.nickname;
    }
}
