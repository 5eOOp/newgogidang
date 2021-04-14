package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.dto.StoreItemDTO;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface StoreService {

    Long register(StoreDTO storeDTO);

    default Map<String, Object> dtoToEntity(StoreDTO storeDTO) {
        Map<String, Object> entityMap = new HashMap<>();
        Store store = Store.builder()
                .sno(storeDTO.getSno())
                .title(storeDTO.getTitle())
                .build();
        entityMap.put("store", store);
        List<StoreItemDTO> itemDTOList = storeDTO.getItemDTOList();
        if(itemDTOList != null && itemDTOList.size() > 0) {
            List<StoreItem> storeItemList = itemDTOList.stream().map(storeItemDTO -> {
                StoreItem storeItem = StoreItem.builder()
                        .path(storeItemDTO.getPath())
                        .imgName(storeItemDTO.getImgName())
                        .uuid(storeItemDTO.getUuid())
                        .store(store)
                        .build();
                return storeItem;
            }).collect(Collectors.toList());

            entityMap.put("itemList", storeItemList);
        }
        return entityMap;
    }
}
