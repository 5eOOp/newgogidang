package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.entity.Event;

import java.util.List;

public interface EventService {

    PageResultDTO<EventDTO, Object[]> getList(PageRequestDTO requestDTO);

    default Event dtoToEntity(EventDTO eventDTO) {
        Event event = Event.builder()
                .title(eventDTO.getTitle())
                .content(eventDTO.getContent())
                .build();

        return event;
    }

    default EventDTO entityToDTO(Event event) {
        EventDTO eventDTO = EventDTO.builder()
                .title(event.getTitle())
                .content(event.getContent())
                .build();

        return eventDTO;
    }
}
