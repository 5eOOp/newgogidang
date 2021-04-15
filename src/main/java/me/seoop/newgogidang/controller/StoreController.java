package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.service.StoreService;
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
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/register")
    public String register() {
        return "store/register";
    }

    @PostMapping("/register")
    public String registerPost(StoreDTO storeDTO, RedirectAttributes redirectAttributes) {
        log.info("storeDTO: " + storeDTO);
        Long sno = storeService.register(storeDTO);
        redirectAttributes.addFlashAttribute("msg", sno);
        return "redirect:/store/list";
    }

    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", storeService.getList(pageRequestDTO));

        return "store/list";
    }

    @GetMapping("/read")
    public String read(long sno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("sno: " + sno);
        StoreDTO storeDTO = storeService.getStore(sno);
        model.addAttribute("dto", storeDTO);
        return "store/read";
    }

    @GetMapping("/modify")
    public String modify(long sno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("sno: " + sno);
        StoreDTO storeDTO = storeService.getStore(sno);
        model.addAttribute("dto", storeDTO);
        return "store/modify";
    }
}
