package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import me.seoop.newgogidang.dto.StoreItemDTO;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreItem;
import me.seoop.newgogidang.repository.StoreItemRepository;
import me.seoop.newgogidang.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreItemServiceImpl implements StoreItemService{

    private final StoreItemRepository storeItemRepository;

    private final StoreRepository storeRepository;

    @Override
    public Long register(StoreItemDTO storeItemDTO) {
        Optional<Store> result = storeRepository.findById(storeItemDTO.getSno());
        Store store = new Store();

        if (result.isPresent()) {
            store = result.get();
        }

        StoreItem storeItem = StoreItem.builder()
                .path(storeItemDTO.getPath())
                .imgName(storeItemDTO.getImgName())
                .uuid(storeItemDTO.getUuid())
                .itemName(storeItemDTO.getItemName())
                .itemGrade(storeItemDTO.getItemGrade())
                .itemPrice(storeItemDTO.getItemPrice())
                .store(store)
                .build();

        storeItemRepository.save(storeItem);

        return storeItemDTO.getSno();
    }
}
