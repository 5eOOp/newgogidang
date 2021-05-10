package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.ReviewDTO;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.Review;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.repository.MemberRepository;
import me.seoop.newgogidang.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ReviewDTO> getListOfStore(Long sno) {
        Store store = Store.builder().sno(sno).build();
        List<Review> result = reviewRepository.findByStore(store);

        return result.stream().map(review -> entityToDTO(review)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);
        reviewRepository.save(review);
        return review.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO storeReviewDTO) {
        Optional<Review> result = reviewRepository.findById(storeReviewDTO.getReviewnum());

        if (result.isPresent()) {
            Review review = result.get();
            review.changeGrade(storeReviewDTO.getGrade());
            review.changeText(storeReviewDTO.getText());
            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }

    @Override
    public List<ReviewDTO> getListOfMember(Long mid) {
        Optional<Member> result = memberRepository.findById(mid);
        if (result.isPresent()) {
            Member member = result.get();
            List<Review> reviewByMember = reviewRepository.findByMember(member);
            return reviewByMember.stream().map(review -> entityToDTO(review)).collect(Collectors.toList());
        }
        return null;
    }
}
