package me.seoop.newgogidang.service;

import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.SellerRequest;
import me.seoop.newgogidang.repository.MemberRepository;
import me.seoop.newgogidang.repository.SellerRequestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SellerRequestServiceTest {

    @Autowired
    private SellerRequestRepository sellerRequestRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testAddSellerRequest() throws Exception {
        Member member = Member.builder()
                .email("user@aaa.com")
                .pw("1111")
                .nickname("usernick")
                .build();
        memberRepository.save(member);

        SellerRequest sellerRequest = SellerRequest.builder()
                .member(member)
                .build();
        sellerRequestRepository.save(sellerRequest);
    }

    @Test
    public void testGetList() throws Exception {
        List<SellerRequest> result = sellerRequestRepository.findAll();
        for (SellerRequest sellerRequest : result) {
            System.out.println(sellerRequest);
        }
    }

}