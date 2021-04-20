package me.seoop.newgogidang.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.repository.MemberRepository;
import me.seoop.newgogidang.security.dto.AuthMemberDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("MemberUserDetailsService loadUserByUsername: " + username);
        Optional<Member> result = memberRepository.findByEmail(username, false);

        log.info("result.isPresent : " + result.isPresent());
        if (!(result.isPresent())) {
            throw new UsernameNotFoundException("Check Email or Social");
        }

        Member member = result.get();
        log.info("-----login-----");
        log.info("member: " + member);

        AuthMemberDTO authMemberDTO = new AuthMemberDTO(
                member.getEmail(),
                member.getPw(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
        );
        authMemberDTO.setNickname(member.getNickname());
        authMemberDTO.setFromSocial(member.isFromSocial());

        return authMemberDTO;
    }
}
