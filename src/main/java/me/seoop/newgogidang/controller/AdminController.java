package me.seoop.newgogidang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.entity.SellerRequest;
import me.seoop.newgogidang.service.SellerRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final SellerRequestService sellerRequestService;

    @GetMapping("/sellerRequestList")
    public String sellerRequestList(Model model) {
        List<SellerRequest> result = sellerRequestService.getList();
        log.info("result: " + result);
        model.addAttribute("result", sellerRequestService.getList());

        return "board/admin/waiting";
    }

    @GetMapping("/permit")
    public String sellerRequestPermit(Long srno) {
        sellerRequestService.permitRequest(srno);

        return "redirect:/admin/sellerRequestList";
    }

    @GetMapping("/refuse")
    public String sellerRequestRefuse(Long srno) {
        sellerRequestService.dismissRequest(srno);
        return "redirect:/admin/sellerRequestList";
    }
}
