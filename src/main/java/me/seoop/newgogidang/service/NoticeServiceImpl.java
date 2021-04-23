package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.NoticeDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Event;
import me.seoop.newgogidang.entity.Notice;
import me.seoop.newgogidang.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("Nno").descending());
        Page<Object[]> result = noticeRepository.getListPage(pageable);
        Function<Object[], NoticeDTO> fn = (arr -> entityToDTO(
                (Notice) arr[0]
        ));
        return new PageResultDTO<>(result, fn);
    }
}
