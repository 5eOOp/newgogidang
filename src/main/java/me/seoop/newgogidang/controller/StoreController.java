package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.service.StoreItemService;
import me.seoop.newgogidang.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;
    private final StoreItemService storeItemService;

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

    @GetMapping("/listAvg")
    public String listWithAvgDesc(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", storeService.getListPageWithAvgDesc(pageRequestDTO));

        return "store/list";
    }

    @GetMapping("/listCount")
    public String listWithCountDesc(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", storeService.getListPageWithCountDesc(pageRequestDTO));

        return "store/list";
    }

    @GetMapping("/slist")
    public String slist(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);
        model.addAttribute("result", storeService.getSearchList(pageRequestDTO));

        return "store/search_list";
    }

    @GetMapping("/read")
    public String read(long sno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("sno: " + sno);
        int count = storeItemService.count(sno);
        log.info("count: " + count);
        if (count != 0) {
            StoreDTO storeDTO = storeService.getStore(sno);
            model.addAttribute("dto", storeDTO);
            return "store/read";
        } else {
            StoreDTO storeDTO = storeService.getStoreFirst(sno);
            model.addAttribute("dto", storeDTO);
            return "store/read";
        }
    }

    @GetMapping("/modify")
    public String modify(long sno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("sno: " + sno);
        StoreDTO storeDTO = storeService.getStore(sno);
        model.addAttribute("dto", storeDTO);
        return "store/modify";
    }

    @PostMapping("/modify/{sno}")
    public ResponseEntity<Long> modifyPut(@RequestBody StoreDTO storeDTO) {
        log.info("-----modify store-----");
        log.info("storeDTO: " + storeDTO);
        storeService.modify(storeDTO);
        return new ResponseEntity<>(storeDTO.getSno(), HttpStatus.OK);
    }
}
