package me.seoop.newgogidang.repository;

import me.seoop.newgogidang.entity.Store;
import me.seoop.newgogidang.entity.StoreImg;
import me.seoop.newgogidang.entity.StoreItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreImgRepository storeImgRepository;

    @Autowired
    private StoreItemRepository storeItemRepository;

    @Commit
    @Transactional
    @Test
    public void insertStore() throws Exception {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Store store = Store.builder()
                    .title("Store..." + i)
                    .address("Address..." + i)
                    .phone("Phone..." + i)
                    .build();
            System.out.println("--------------------");
            storeRepository.save(store);

            StoreImg storeImg = StoreImg.builder()
                    .uuid(UUID.randomUUID().toString())
                    .imgName("test" + i + ".jpg")
                    .store(store)
                    .build();
            storeImgRepository.save(storeImg);

            int count = (int)(Math.random() * 5) + 1;
            for (int j = 0; j < count; j++) {
                StoreItem storeItem = StoreItem.builder()
                        .uuid(UUID.randomUUID().toString())
                        .store(store)
                        .imgName("test" + j + ".jpg")
                        .itemName("test..." + i + "item")
                        .itemGrade(i + "등급")
                        .itemPrice(i + "000원")
                        .build();

                storeItemRepository.save(storeItem);
            }
            System.out.println("--------------------");
        });
    }

//    @Test
//    public void testListPage() throws Exception {
//        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "sno"));
//        Page<Object[]> result = storeRepository.getListPage(pageRequest);
//        for (Object[] objects : result) {
//            System.out.println(Arrays.toString(objects));
//        }
//    }

    @Test
    public void testGetStoreWithAll() throws Exception {
        List<Object[]> result = storeRepository.getStoreWithAll(94L);
        System.out.println(result);
        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

}