package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void insertNotice() throws Exception {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Notice notice = Notice.builder()
                    .title("Notice title " + i)
                    .content("Notice content " + i)
                    .build();

            noticeRepository.save(notice);
        });
    }

}