package me.seoop.newgogidang.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.MemberRole;
import me.seoop.newgogidang.repository.MemberRepository;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("-----OAuth2UserDetailsService-----");
        log.info("userRequest: " + userRequest); // userRequest: org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@b207fe

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info( "clientName: " + clientName); // clientName: Google
        log.info(String.valueOf(userRequest.getAdditionalParameters()));

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("-----oAuth2User-----");
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info("k: " + k +", v: " + v);
            // k: sub, v: 105871976484082510345
            // k: picture, v: https://lh3.googleusercontent.com/a/default-user=s96-c
            // k: email, v: kimth0526@gmail.com
            // k: email_verified, v: true
        });

        String email = null;

        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }
        log.info("Email: " + email);
//        Member member = saveSocialMember(email);
//        return oAuth2User;
        Member member = saveSocialMember(email);
        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPw(),
                true,
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes()
        );
        authMemberDTO.setNickname(member.getNickname());

        return authMemberDTO;
    }

    private Member saveSocialMember(String email) {
        Optional<Member> result = memberRepository.findByEmail(email, true);

        int idx = email.indexOf("@");
        String nickname = email.substring(0, idx);

        if (result.isPresent()) {
            return result.get();
        }

        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .pw(passwordEncoder.encode("1111"))
                .build();
        member.addMemberRole(MemberRole.USER);
        memberRepository.save(member);
        return member;
    }
}
