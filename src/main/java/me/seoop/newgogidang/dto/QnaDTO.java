package me.seoop.newgogidang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaDTO {

    private Long qno;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
