package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertMembers() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .pw("1111")
                    .nickname("usernick" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void insertDummies() throws Exception {
        // 1 - 80은 USER
        // 81 - 90은 USER, SELLER
        // 91 - 100은 USER, SELLER, ADMIN

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@aaa.com")
                    .nickname("nickname" + i)
                    .fromSocial(false)
                    .pw(passwordEncoder.encode("1111"))
                    .build();
            member.addMemberRole(MemberRole.USER);
            if (i > 80) {
                member.addMemberRole(MemberRole.SELLER);
            }
            if (i > 90) {
                member.addMemberRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });
    }

//    @Test
//    public void testRead() throws Exception {
//        Optional<Member> result = memberRepository.findByEmail("user95@aaa.com", false);
//        Member member = result.get();
//        System.out.println(member);
//    }
//
//    @Commit
//    @Transactional
//    @Test
//    public void testDeleteMember() throws Exception {
//        Long mid = 1L;
//        Member member = Member.builder().mid(mid).build();
//
//        reviewRepository.deleteByMember(member);
//        memberRepository.deleteById(mid);
//    }

}