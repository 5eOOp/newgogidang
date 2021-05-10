package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long register(MemberDTO memberDTO) {
        Member member = dtoToEntity(memberDTO);
        memberRepository.save(member);
        return member.getMid();
    }

    @Override
    public int login(MemberDTO memberDTO) {
        int count = memberRepository.loginCheck(memberDTO.getEmail(), memberDTO.getPw());
        return count;
    }

    @Override
    public MemberDTO getMember(Long mid) {
        Member member = memberRepository.getOne(mid);
        MemberDTO memberDTO = entityToDTO(member);

        return memberDTO;
    }

    @Override
    public void modify(MemberDTO memberDTO) {
        Optional<Member> result = memberRepository.findById(memberDTO.getMid());
        if (result.isPresent()) {
            Member member = result.get();
            member.changePw(memberDTO.getPw());
            member.changeNickname(memberDTO.getNickname());
            memberRepository.save(member);
        }
    }

    @Override
    public void remove(Long mid) {
        memberRepository.deleteById(mid);
    }

    @Override
    public MemberDTO findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);
        MemberDTO memberDTO = entityToDTO(member);
        return memberDTO;
    }

}
