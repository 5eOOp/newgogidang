package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreItem;
import me.seoop.newgogidang.repository.StoreItemRepository;
import me.seoop.newgogidang.repository.StoreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreItemRepository storeItemRepository;

    @Transactional
    @Override
    public Long register(StoreDTO storeDTO) {
        Map<String, Object> entityMap = dtoToEntity(storeDTO);
        Store store = (Store) entityMap.get("store");
        storeRepository.save(store);

        List<StoreItem> itemList = (List<StoreItem>) entityMap.get("itemList");
        itemList.forEach(item -> {
            storeItemRepository.save(item);
        });

        return store.getSno();
    }
}
