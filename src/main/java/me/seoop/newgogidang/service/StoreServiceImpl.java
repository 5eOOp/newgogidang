package me.seoop.newgogidang.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.EventDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.StoreDTO;
import me.seoop.newgogidang.entity.*;
import me.seoop.newgogidang.repository.MemberRepository;
import me.seoop.newgogidang.repository.StoreImgRepository;
import me.seoop.newgogidang.repository.StoreItemRepository;
import me.seoop.newgogidang.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Long register(StoreDTO storeDTO, Long mid) {
        Map<String, Object> entityMap = dtoToEntity(storeDTO);
        Optional<Member> result = memberRepository.findById(mid);

        Store store = (Store) entityMap.get("store");
        storeRepository.save(store);

        if (result.isPresent()) {
            Member member = result.get();
            member.setStore(store);
            memberRepository.save(member);
        }

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
        Function<Object[], StoreDTO> fn = (arr -> entitiesToDTOList(
                (Store) arr[0],
                (List<StoreImg>) (Arrays.asList((StoreImg) arr[1])),
                (Double) arr[2],
                (Long)  arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<StoreDTO, Store> getSearchList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색조건
        Page<Store> result = storeRepository.findAll(booleanBuilder, pageable);
        Function<Store, StoreDTO> fn = (store -> entityToDTO(store));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<StoreDTO, Object[]> getListPageWithAvgDesc(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sno").descending());
        Page<Object[]> result = storeRepository.getListPageWithAvgDesc(pageable);
        Function<Object[], StoreDTO> fn = (arr -> entitiesToDTOList(
                (Store) arr[0],
                (List<StoreImg>) (Arrays.asList((StoreImg) arr[1])),
                (Double) arr[2],
                (Long)  arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<StoreDTO, Object[]> getListPageWithCountDesc(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("sno").descending());
        Page<Object[]> result = storeRepository.getListPageWithCountDesc(pageable);
        Function<Object[], StoreDTO> fn = (arr -> entitiesToDTOList(
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
        log.info("length: " + result.get(0).length);
        Store store = (Store) result.get(0)[0];

        List<StoreImg> storeImgList = new ArrayList<>();
        result.forEach(arr -> {
            StoreImg storeImg = (StoreImg) arr[1];
            storeImgList.add(storeImg);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        List<StoreItem> storeItemList = new ArrayList<>();
        result.forEach(arr -> {
            StoreItem storeItem = (StoreItem) arr[4];
            storeItemList.add(storeItem);
        });
        log.info("storeItemList size: " + storeItemList.size());

        return entitiesToDTOStore(store, storeImgList, avg, reviewCnt, storeItemList);
    }

    @Override
    public StoreDTO getStoreFirst(Long sno) {
        List<Object[]> result = storeRepository.getStoreWithAll(sno);
        log.info("length: " + result.get(0).length);
        Store store = (Store) result.get(0)[0];

        List<StoreImg> storeImgList = new ArrayList<>();
        result.forEach(arr -> {
            StoreImg storeImg = (StoreImg) arr[1];
            storeImgList.add(storeImg);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];

        return entitiesToDTOList(store, storeImgList, avg, reviewCnt);
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

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QStore qStore = QStore.store;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qStore.sno.gt(0L);
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        BooleanBuilder condtionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            condtionBuilder.or(qStore.title.contains(keyword));
        }
        if (type.contains("c")) {
            condtionBuilder.or(qStore.address.contains(keyword));
        }

        booleanBuilder.and(condtionBuilder);

        return booleanBuilder;
    }
}
