package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
}
