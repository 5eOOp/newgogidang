package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.Member;

public interface MemberService {

    Long register(MemberDTO memberDTO);
    int login(MemberDTO memberDTO);


    default Member dtoToEntity(MemberDTO memberDTO) {
        Member member = Member.builder()
            .email(memberDTO.getEmail())
            .pw(memberDTO.getPw())
            .nickname(memberDTO.getNickname())
            .build();

        return member;
    }
}
