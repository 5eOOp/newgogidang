package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.QnaDTO;
import me.seoop.newgogidang.service.QnaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        return "board/qna/qna";
    }

    @GetMapping("register")
    public String registerForm() {
        return "board/qna/register";
    }

    @PostMapping("register")
    public String register(QnaDTO dto, RedirectAttributes redirectAttributes) {

        log.info("dto..." + dto);
        Long qno = qnaService.register(dto);
        redirectAttributes.addFlashAttribute("msg", qno);

        return "redirect:/qna/list";
    }

    @GetMapping("/read")
    public String getQna(Long qno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("qno: " + qno);
        QnaDTO qnaDTO = qnaService.getQna(qno);
        model.addAttribute("dto", qnaDTO);

        return "board/qna/read";
    }

    @GetMapping("/modify")
    public String modifyForm(Long qno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("qno: " + qno);
        QnaDTO qnaDTO = qnaService.getQna(qno);
        model.addAttribute("dto", qnaDTO);

        return "board/qna/modify";
    }

    @PostMapping("/modify")
    public String modify(QnaDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify.....");
        log.info("dto: " + dto);
        qnaService.modify(dto);
        redirectAttributes.addAttribute("qno",dto.getQno());
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        return "redirect:/qna/read";
    }

    @PostMapping("/remove")
    public String remove(Long qno, RedirectAttributes redirectAttributes) {
        log.info("remove qno: " + qno);
        qnaService.remove(qno);
        redirectAttributes.addFlashAttribute("msg", qno);

        return "redirect:/qna/list";
    }

}
