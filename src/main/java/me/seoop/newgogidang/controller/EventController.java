package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.service.EventService;
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
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("event pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", eventService.getList(pageRequestDTO));

        return "board/event/event";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "board/event/register";
    }

    @PostMapping("/register")
    public String register(EventDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);
        Long eno = eventService.register(dto);
        redirectAttributes.addFlashAttribute("msg", eno);

        return "redirect:/event/list";
    }

    @GetMapping("/read")
    public String getEvent(Long eno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("eno: " + eno);
        EventDTO eventDTO = eventService.getEvent(eno);
        model.addAttribute("dto", eventDTO);

        return "board/event/read";
    }

    @GetMapping("/modify")
    public String modifyForm(long eno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("eno: " + eno);
        EventDTO eventDTO = eventService.getEvent(eno);
        model.addAttribute("dto", eventDTO);

        return "board/event/modify";
    }

    @PostMapping("/modify")
    public String modify(EventDTO dto,
                         @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        log.info("post modify.....");
        log.info("dto: " + dto);
        eventService.modify(dto);
        redirectAttributes.addAttribute("eno",dto.getEno());
        redirectAttributes.addAttribute("page",requestDTO.getPage());
        redirectAttributes.addAttribute("type",requestDTO.getType());
        redirectAttributes.addAttribute("keyword",requestDTO.getKeyword());

        return "redirect:/event/read";
    }

    @PostMapping("/remove")
    public String remove(long eno, RedirectAttributes redirectAttributes) {
        log.info("remove eno: " + eno);
        eventService.remove(eno);
        redirectAttributes.addFlashAttribute("msg", eno);

        return "redirect:/event/list";
    }
}
