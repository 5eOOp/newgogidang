package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void insertEvent() throws Exception {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Event event = Event.builder()
                    .title("Event title " + i)
                    .content("Event content " + i)
                    .build();

            eventRepository.save(event);
        });
    }

}