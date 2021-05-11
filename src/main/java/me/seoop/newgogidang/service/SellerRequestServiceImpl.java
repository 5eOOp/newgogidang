package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.MemberDTO;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.MemberRole;
import me.seoop.newgogidang.entity.SellerRequest;
import me.seoop.newgogidang.repository.SellerRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerRequestServiceImpl implements SellerRequestService {

    private final SellerRequestRepository sellerRequestRepository;

    @Override
    public void addSeller(MemberDTO memberDTO) {
        Member member = Member.builder()
                .mid(memberDTO.getMid())
                .email(memberDTO.getEmail())
                .nickname(memberDTO.getNickname())
                .build();

        SellerRequest sellerRequest = SellerRequest.builder()
                .member(member)
                .build();

        sellerRequestRepository.save(sellerRequest);
    }

    @Override
    public List<SellerRequest> getList() {
        List<SellerRequest> result = sellerRequestRepository.findAll();
        return result;
    }

    @Override
    public void permitRequest(Long srno) {
        Optional<SellerRequest> result = sellerRequestRepository.findById(srno);
        if (result.isPresent()) {
            SellerRequest sellerRequest = result.get();
            Member member = sellerRequest.getMember();
            member.addMemberRole(MemberRole.SELLER);
        }
        sellerRequestRepository.deleteById(srno);

    }

    @Override
    public void dismissRequest(Long srno) {
        sellerRequestRepository.deleteById(srno);
    }
}
