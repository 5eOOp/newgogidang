package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.NoticeDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Event;
import me.seoop.newgogidang.entity.Notice;

public interface NoticeService {

    PageResultDTO<NoticeDTO, Object[]> getList(PageRequestDTO requestDTO);

    default Notice dtoToEntity(NoticeDTO noticeDTO) {
        Notice notice = Notice.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .build();

        return notice;
    }

    default NoticeDTO entityToDTO(Notice notice) {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();

        return noticeDTO;
    }
}
