package me.seoop.newgogidang.dto;

import lombok.*;
import me.seoop.newgogidang.entity.Store;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long mid;
    private String email;
    private String pw;
    private String nickname;
    private LocalDateTime regDate, modDate;
}
