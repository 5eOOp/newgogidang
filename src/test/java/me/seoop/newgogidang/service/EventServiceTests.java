package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTests {

    @Autowired
    private EventService eventService;

    @Test
    public void testSearch() throws Exception {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("45")
                .build();

        PageResultDTO<EventDTO, Event> resultDTO = eventService.getList(pageRequestDTO);
        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("next: " + resultDTO.isNext());
        System.out.println("total: " + resultDTO.getTotalPage());
        System.out.println("---------------------------");
        for (EventDTO eventDTO : resultDTO.getDtoList()) {
            System.out.println(eventDTO);
        }
        System.out.println("---------------------------");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}