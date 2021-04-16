package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.ReviewDTO;
import me.seoop.newgogidang.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{sno}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("sno") Long sno) {
        log.info("-----list-----");
        log.info("sno: " + sno);
        List<ReviewDTO> reviewDTOList = reviewService.getListOfStore(sno);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{sno}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("-----add Review-----");
        log.info("ReviewDTO: " + reviewDTO);
        Long reviewnum = reviewService.register(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @PutMapping("/{sno}/{reviewnum}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewnum,
                                             @RequestBody ReviewDTO reviewDTO) {
        log.info("-----modify review-----");
        log.info("reviewDTO: " + reviewDTO);
        reviewService.modify(reviewDTO);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }

    @DeleteMapping("/{sno}/{reviewnum}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewnum) {
        log.info("-----remove review-----");
        log.info("reviewnum: " + reviewnum);
        reviewService.remove(reviewnum);
        return new ResponseEntity<>(reviewnum, HttpStatus.OK);
    }
}
