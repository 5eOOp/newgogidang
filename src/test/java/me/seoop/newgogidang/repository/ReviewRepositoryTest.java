package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Member;
import me.seoop.newgogidang.entity.Review;
import me.seoop.newgogidang.entity.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertStoreReview() throws Exception {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long sno = (long) (Math.random()*100) + 1;
            Store store = Store.builder().sno(sno).build();

            Long mid = (long) (Math.random()*100) + 1;
            Member member = Member.builder().mid(mid).build();

            Review review = Review.builder()
                    .member(member)
                    .store(store)
                    .grade((int)(Math.random()*5) + 1)
                    .text("이 음식점 평가..." + i)
                    .build();

            reviewRepository.save(review);
        });
    }

    @Test
    public void testGetStoreReview() throws Exception {
        Store store = Store.builder().sno(94L).build();
        List<Review> result = reviewRepository.findByStore(store);
        result.forEach(storeReview -> {
            System.out.println("storeReview = " + storeReview);
        });
    }

}