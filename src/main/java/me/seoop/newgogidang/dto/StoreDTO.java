package me.seoop.newgogidang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {

    private Long sno;
    private String title;

    @Builder.Default
    private List<StoreItemDTO> itemDTOList = new ArrayList<>();
}
