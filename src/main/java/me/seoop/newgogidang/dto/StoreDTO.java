package me.seoop.newgogidang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {

    private Long sno;
    private String title;
    private String address;
    private String phone;

    private double avg;
    private int reviewCnt;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder.Default
    private List<StoreImgDTO> imgDTOList = new ArrayList<>();

//    @Builder.Default
//    private List<StoreItemDTO> itemDTOList = new ArrayList<>();
}
