package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.SellerRequest;

import java.util.List;

public interface SellerRequestService {

    void addSeller(MemberDTO memberDTO);
    List<SellerRequest> getList();
    void permitRequest(Long srno);
    void dismissRequest(Long srno);
}
