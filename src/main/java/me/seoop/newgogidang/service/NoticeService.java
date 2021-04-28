package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.NoticeDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Notice;

public interface NoticeService {

    PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO);
    NoticeDTO getNotice(Long nno);
    void remove(Long nno);
    void modify(NoticeDTO dto);
    Long register(NoticeDTO dto);

    default Notice dtoToEntity(NoticeDTO noticeDTO) {
        Notice notice = Notice.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .build();

        return notice;
    }

    default NoticeDTO entityToDTO(Notice notice) {
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .nno(notice.getNno())
                .title(notice.getTitle())
                .content(notice.getContent())
                .regDate(notice.getRegDate())
                .modDate(notice.getModDate())
                .build();

        return noticeDTO;
    }
}
