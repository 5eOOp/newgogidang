package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.NoticeDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("notice pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", noticeService.getList(pageRequestDTO));

        return "board/notice";
    }

    @GetMapping("/read")
    public String getNotice(Long nno, Model model) {
        log.info("nno: " + nno);
        NoticeDTO noticeDTO = noticeService.getNotice(nno);
        model.addAttribute("dto", noticeDTO);
        return "board/read";
    }
}
