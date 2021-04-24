package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.QnaDTO;
import me.seoop.newgogidang.service.QnaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("qna pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", qnaService.getList(pageRequestDTO));

        return "board/qna";
    }

    @GetMapping("/read")
    public String getQna(Long qno, Model model) {
        log.info("qno: " + qno);
        QnaDTO qnaDTO = qnaService.getQna(qno);
        model.addAttribute("dto", qnaDTO);
        return "board/read";
    }

}
