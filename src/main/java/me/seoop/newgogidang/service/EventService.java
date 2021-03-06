package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Event;

public interface EventService {

    PageResultDTO<EventDTO, Event> getList(PageRequestDTO requestDTO);
    EventDTO getEvent(Long eno);
    void remove(Long eno);
    void modify(EventDTO dto);
    Long register(EventDTO dto);

    default Event dtoToEntity(EventDTO eventDTO) {
        Event event = Event.builder()
                .title(eventDTO.getTitle())
                .content(eventDTO.getContent())
                .build();

        return event;
    }

    default EventDTO entityToDTO(Event event) {
        EventDTO eventDTO = EventDTO.builder()
                .eno(event.getEno())
                .title(event.getTitle())
                .content(event.getContent())
                .regDate(event.getRegDate())
                .modDate(event.getModDate())
                .build();

        return eventDTO;
    }
}
