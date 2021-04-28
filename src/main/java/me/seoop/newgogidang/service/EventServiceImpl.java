package me.seoop.newgogidang.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Event;
import me.seoop.newgogidang.entity.QEvent;
import me.seoop.newgogidang.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public PageResultDTO<EventDTO, Event> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("eno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색조건
        Page<Event> result = eventRepository.findAll(booleanBuilder, pageable);
        Function<Event, EventDTO> fn = (event -> entityToDTO(event));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public EventDTO getEvent(Long eno) {
        Event event = eventRepository.getOne(eno);
        EventDTO eventDTO = entityToDTO(event);
        return eventDTO;
    }

    @Override
    public void remove(Long eno) {
        eventRepository.deleteById(eno);
    }

    @Override
    public void modify(EventDTO dto) {
        Optional<Event> result = eventRepository.findById(dto.getEno());
        if (result.isPresent()) {
            Event event = result.get();
            event.changeTitle(dto.getTitle());
            event.changeContent(dto.getContent());
            eventRepository.save(event);
        }
    }

    @Override
    public Long register(EventDTO dto) {
        log.info("register event.....");
        log.info("event dto: " + dto);
        Event event = dtoToEntity(dto);
        log.info("event: " + event);
        eventRepository.save(event);

        return event.getEno();
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QEvent qEvent = QEvent.event;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qEvent.eno.gt(0L);
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        BooleanBuilder condtionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            condtionBuilder.or(qEvent.title.contains(keyword));
        }
        if (type.contains("c")) {
            condtionBuilder.or(qEvent.content.contains(keyword));
        }

        booleanBuilder.and(condtionBuilder);

        return booleanBuilder;
    }
}
