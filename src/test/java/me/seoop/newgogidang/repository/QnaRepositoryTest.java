package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Qna;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class QnaRepositoryTest {

    @Autowired
    private QnaRepository qnaRepository;

    @Test
    public void insertQna() throws Exception {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Qna qna = Qna.builder()
                    .title("Qna title " + i)
                    .content("Qna content " + i)
                    .build();

            qnaRepository.save(qna);
        });
    }

}