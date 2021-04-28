package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.NoticeDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.service.NoticeService;
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
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("notice pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", noticeService.getList(pageRequestDTO));

        return "board/notice/notice";
    }

    @GetMapping("register")
    public String registerForm() {
        return "board/notice/register";
    }

    @PostMapping("register")
    public String register(NoticeDTO dto, RedirectAttributes redirectAttributes) {

        log.info("dto..." + dto);
        Long nno = noticeService.register(dto);
        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/notice/list";
    }

    @GetMapping("/read")
    public String getNotice(Long nno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("nno: " + nno);
        NoticeDTO noticeDTO = noticeService.getNotice(nno);
        model.addAttribute("dto", noticeDTO);

        return "board/notice/read";
    }

    @GetMapping("/modify")
    public String modifyForm(Long nno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("nno: " + nno);
        NoticeDTO noticeDTO = noticeService.getNotice(nno);
        model.addAttribute("dto", noticeDTO);

        return "board/notice/modify";
    }

    @PostMapping("/modify")
    public String modify(NoticeDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify.....");
        log.info("dto: " + dto);
        noticeService.modify(dto);
        redirectAttributes.addAttribute("nno",dto.getNno());
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        return "redirect:/notice/read";
    }

    @PostMapping("/remove")
    public String remove(Long nno, RedirectAttributes redirectAttributes) {
        log.info("remove nno: " + nno);
        noticeService.remove(nno);
        redirectAttributes.addFlashAttribute("msg", nno);

        return "redirect:/notice/list";
    }
}
