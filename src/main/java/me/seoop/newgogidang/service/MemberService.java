package me.seoop.newgogidang.service;

import com.sun.javadoc.MemberDoc;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.Member;

public interface MemberService {

    Long register(MemberDTO memberDTO);
    int login(MemberDTO memberDTO);
    MemberDTO getMember(Long mid);
    void modify(MemberDTO memberDTO);
    void remove(Long mid);


    default Member dtoToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
            .email(memberDTO.getEmail())
            .pw(memberDTO.getPw())
            .nickname(memberDTO.getNickname())
            .build();

        return member;
    }

    default MemberDTO entityToDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .mid(member.getMid())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .regDate(member.getRegDate())
                .modDate(member.getModDate())
                .build();

        return memberDTO;
    }
}
