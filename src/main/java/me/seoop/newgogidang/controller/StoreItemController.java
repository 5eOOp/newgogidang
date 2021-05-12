package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.StoreItemDTO;
import me.seoop.newgogidang.service.StoreItemService;
import me.seoop.newgogidang.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/item")
public class StoreItemController {

    private final StoreItemService storeItemService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody StoreItemDTO storeItemDTO) {
        log.info("-----register item-----");
        log.info("storeItemDTO: " + storeItemDTO);
        Long result = storeItemService.register(storeItemDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/deleteItem")
    public String deleteItem(Long inum) {
        log.info("inum: " + inum);
        storeItemService.deleteItem(inum);
        return "redirect:/store/mystore";
    }
}
