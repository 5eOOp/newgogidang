package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.entity.Review;
import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreImg;
import me.seoop.newgogidang.entity.StoreItem;
import me.seoop.newgogidang.repository.StoreImgRepository;
import me.seoop.newgogidang.repository.StoreItemRepository;
import me.seoop.newgogidang.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreItemRepository storeItemRepository;
    private final StoreImgRepository storeImgRepository;

    @Transactional
    @Override
    public Long register(StoreDTO storeDTO) {
        Map<String, Object> entityMap = dtoToEntity(storeDTO);

        Store store = (Store) entityMap.get("store");
        storeRepository.save(store);

        List<StoreImg> imgList = (List<StoreImg>) entityMap.get("imgList");
        imgList.forEach(img -> {
            storeImgRepository.save(img);
        });

        return store.getSno();
    }

    @Override
    public PageResultDTO<StoreDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sno").descending());
        Page<Object[]> result = storeRepository.getListPage(pageable);
        Function<Object[], StoreDTO> fn = (arr -> entitiesToDTO(
                (Store) arr[0],
                (List<StoreImg>) (Arrays.asList((StoreImg) arr[1])),
                (Double) arr[2],
                (Long)  arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public StoreDTO getStore(Long sno) {
        List<Object[]> result = storeRepository.getStoreWithAll(sno);
        Store store = (Store) result.get(0)[0];

        List<StoreImg> storeImgList = new ArrayList<>();
        result.forEach(arr -> {
            StoreImg storeImg = (StoreImg) arr[1];
            storeImgList.add(storeImg);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDTO(store, storeImgList, avg, reviewCnt);
    }

    @Override
    public void modify(StoreDTO storeDTO) {
        Optional<Store> result = storeRepository.findById(storeDTO.getSno());

        if (result.isPresent()) {
            Store store = result.get();
            store.changeTitle(storeDTO.getTitle());
            store.changeAddress(storeDTO.getAddress());
            store.changePhone(storeDTO.getPhone());
            storeRepository.save(store);
        }
    }
}
