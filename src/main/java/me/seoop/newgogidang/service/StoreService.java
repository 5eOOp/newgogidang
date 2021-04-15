package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.dto.StoreItemDTO;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreItem;

import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface StoreService {

    Long register(StoreDTO storeDTO);
    PageResultDTO<StoreDTO, Object[]> getList(PageRequestDTO requestDTO);
    StoreDTO getStore(Long sno);

    default StoreDTO entitiesToDTO(Store store, List<StoreItem> storeItems, Double avg, Long reviewCnt) {
        StoreDTO storeDTO = StoreDTO.builder()
                .sno(store.getSno())
                .title(store.getTitle())
                .regDate(store.getRegDate())
                .modDate(store.getModDate())
                .build();
        List<StoreItemDTO> storeItemDTOList = storeItems.stream().map(storeItem -> {
            return StoreItemDTO.builder()
                    .imgName(storeItem.getImgName())
                    .path(storeItem.getPath())
                    .uuid(storeItem.getUuid())
                    .build();
        }).collect(Collectors.toList());
        storeDTO.setItemDTOList(storeItemDTOList);
        storeDTO.setAvg(avg);
        storeDTO.setReviewCnt(reviewCnt.intValue());

        return storeDTO;
    }

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
