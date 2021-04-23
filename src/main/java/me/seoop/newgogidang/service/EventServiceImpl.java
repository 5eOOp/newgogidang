package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.entity.Event;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreImg;
import me.seoop.newgogidang.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public PageResultDTO<EventDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("eno").descending());
        Page<Object[]> result = eventRepository.getListPage(pageable);
        Function<Object[], EventDTO> fn = (arr -> entityToDTO(
                (Event) arr[0]
        ));
        return new PageResultDTO<>(result, fn);
    }
}
