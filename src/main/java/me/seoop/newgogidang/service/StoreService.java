package me.seoop.newgogidang.service;

import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.*;
import me.seoop.newgogidang.entity.Event;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreImg;
import me.seoop.newgogidang.entity.StoreItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface StoreService {

    Long register(StoreDTO storeDTO, Long mid);
    PageResultDTO<StoreDTO, Object[]> getList(PageRequestDTO requestDTO);
    PageResultDTO<StoreDTO, Store> getSearchList(PageRequestDTO requestDTO);
    PageResultDTO<StoreDTO, Object[]> getListPageWithAvgDesc(PageRequestDTO requestDTO);
    PageResultDTO<StoreDTO, Object[]> getListPageWithCountDesc(PageRequestDTO requestDTO);
    StoreDTO getStore(Long sno);
    StoreDTO getStoreFirst(Long sno);
    void modify(StoreDTO storeDTO);

    default StoreDTO entitiesToDTOStore(Store store, List<StoreImg> storeImages, Double avg, Long reviewCnt, List<StoreItem> storeItems) {
        StoreDTO storeDTO = StoreDTO.builder()
                .sno(store.getSno())
                .title(store.getTitle())
                .address(store.getAddress())
                .phone(store.getPhone())
                .regDate(store.getRegDate())
                .modDate(store.getModDate())
                .build();

        List<StoreImgDTO> storeImgDTOList = storeImages.stream().map(storeImg -> {
            return StoreImgDTO.builder()
                    .imgName(storeImg.getImgName())
                    .path(storeImg.getPath())
                    .uuid(storeImg.getUuid())
                    .build();
        }).collect(Collectors.toList());

        List<StoreItemDTO> storeItemDTOList = storeItems.stream().map(storeItem -> {
            return StoreItemDTO.builder()
                    .imgName(storeItem.getImgName())
                    .path(storeItem.getPath())
                    .uuid(storeItem.getUuid())
                    .itemName(storeItem.getItemName())
                    .itemPrice(storeItem.getItemPrice())
                    .itemGrade(storeItem.getItemGrade())
                    .build();
        }).collect(Collectors.toList());

        storeDTO.setImgDTOList(storeImgDTOList);
        storeDTO.setItemDTOList(storeItemDTOList);
        storeDTO.setAvg(avg);
        storeDTO.setReviewCnt(reviewCnt.intValue());

        return storeDTO;
    }

    default StoreDTO entitiesToDTOList(Store store, List<StoreImg> storeImages, Double avg, Long reviewCnt) {
        StoreDTO storeDTO = StoreDTO.builder()
                .sno(store.getSno())
                .title(store.getTitle())
                .address(store.getAddress())
                .phone(store.getPhone())
                .regDate(store.getRegDate())
                .modDate(store.getModDate())
                .build();

        List<StoreImgDTO> storeImgDTOList = storeImages.stream().map(storeImg -> {
            return StoreImgDTO.builder()
                    .imgName(storeImg.getImgName())
                    .path(storeImg.getPath())
                    .uuid(storeImg.getUuid())
                    .build();
        }).collect(Collectors.toList());

        storeDTO.setImgDTOList(storeImgDTOList);
        storeDTO.setAvg(avg);
        storeDTO.setReviewCnt(reviewCnt.intValue());

        return storeDTO;
    }

    default Map<String, Object> dtoToEntity(StoreDTO storeDTO) {
        Map<String, Object> entityMap = new HashMap<>();

        Store store = Store.builder()
                .sno(storeDTO.getSno())
                .title(storeDTO.getTitle())
                .address(storeDTO.getAddress())
                .phone(storeDTO.getPhone())
                .build();
        entityMap.put("store", store);

        List<StoreImgDTO> imgDTOList = storeDTO.getImgDTOList();
        if(imgDTOList != null && imgDTOList.size() > 0) {
            List<StoreImg> storeImgList = imgDTOList.stream().map(storeImgDTO -> {
                StoreImg storeImg = StoreImg.builder()
                        .path(storeImgDTO.getPath())
                        .imgName(storeImgDTO.getImgName())
                        .uuid(storeImgDTO.getUuid())
                        .store(store)
                        .build();
                return storeImg;
            }).collect(Collectors.toList());

            entityMap.put("imgList", storeImgList);
        }

        return entityMap;
    }

    default StoreDTO entityToDTO(Store store) {
        StoreDTO storeDTO = StoreDTO.builder()
                .sno(store.getSno())
                .title(store.getTitle())
                .address(store.getAddress())
                .regDate(store.getRegDate())
                .build();

        return storeDTO;
    }

//    default StoreDTO entitiesToDTO(Store store, List<StoreItem> storeItems, Double avg, Long reviewCnt) {
//        StoreDTO storeDTO = StoreDTO.builder()
//                .sno(store.getSno())
//                .title(store.getTitle())
//                .regDate(store.getRegDate())
//                .modDate(store.getModDate())
//                .build();
//        List<StoreItemDTO> storeItemDTOList = storeItems.stream().map(storeItem -> {
//            return StoreItemDTO.builder()
//                    .imgName(storeItem.getImgName())
//                    .path(storeItem.getPath())
//                    .uuid(storeItem.getUuid())
//                    .build();
//        }).collect(Collectors.toList());
//        storeDTO.setItemDTOList(storeItemDTOList);
//        storeDTO.setAvg(avg);
//        storeDTO.setReviewCnt(reviewCnt.intValue());
//
//        return storeDTO;
//    }

//    default Map<String, Object> dtoToEntity(StoreDTO storeDTO) {
//        Map<String, Object> entityMap = new HashMap<>();
//
//        Store store = Store.builder()
//                .sno(storeDTO.getSno())
//                .title(storeDTO.getTitle())
//                .build();
//        entityMap.put("store", store);
//
//        List<StoreItemDTO> itemDTOList = storeDTO.getItemDTOList();
//        if(itemDTOList != null && itemDTOList.size() > 0) {
//            List<StoreItem> storeItemList = itemDTOList.stream().map(storeItemDTO -> {
//                StoreItem storeItem = StoreItem.builder()
//                        .path(storeItemDTO.getPath())
//                        .imgName(storeItemDTO.getImgName())
//                        .uuid(storeItemDTO.getUuid())
//                        .store(store)
//                        .build();
//                return storeItem;
//            }).collect(Collectors.toList());
//
//            entityMap.put("itemList", storeItemList);
//        }
//        return entityMap;
//    }
}
