package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.StoreItemDTO;

public interface StoreItemService {

    Long register(StoreItemDTO storeItemDTO);
    int count(Long sno);
    void deleteItem(Long inum);
}
