package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.ReviewDTO;
import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.Review;
import me.seoop.newgogidang.entity.Store;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getListOfStore(Long sno);
    Long register(ReviewDTO reviewDTO);
    void modify(ReviewDTO storeReviewDTO);
    void remove(Long reviewnum);

    default Review dtoToEntity(ReviewDTO storeReviewDTO) {
        Review storeReview = Review.builder()
                .reviewnum(storeReviewDTO.getReviewnum())
                .store(Store.builder().sno(storeReviewDTO.getSno()).build())
                .member(Member.builder().mid(storeReviewDTO.getMid()).build())
                .grade(storeReviewDTO.getGrade())
                .text(storeReviewDTO.getText())
                .build();
        return storeReview;
    }

    default ReviewDTO entityToDTO(Review storeReview) {
        ReviewDTO storeReviewDTO = ReviewDTO.builder()
                .reviewnum(storeReview.getReviewnum())
                .sno(storeReview.getStore().getSno())
                .mid(storeReview.getMember().getMid())
                .nickname(storeReview.getMember().getNickname())
                .email(storeReview.getMember().getEmail())
                .grade(storeReview.getGrade())
                .text(storeReview.getText())
                .regDate(storeReview.getRegDate())
                .modDate(storeReview.getModDate())
                .build();
        return storeReviewDTO;
    }
}
