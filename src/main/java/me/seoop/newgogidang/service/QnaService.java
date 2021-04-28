package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.QnaDTO;
import me.seoop.newgogidang.entity.Qna;

public interface QnaService {

    PageResultDTO<QnaDTO, Qna> getList(PageRequestDTO requestDTO);
    QnaDTO getQna(Long qno);
    void remove(Long qno);
    void modify(QnaDTO dto);
    Long register(QnaDTO dto);

    default Qna dtoToEntity(QnaDTO qnaDTO) {
        Qna qna = Qna.builder()
                .title(qnaDTO.getTitle())
                .content(qnaDTO.getContent())
                .build();

        return qna;
    }

    default QnaDTO entityToDTO(Qna qna) {
        QnaDTO qnaDTO = QnaDTO.builder()
                .qno(qna.getQno())
                .title(qna.getTitle())
                .content(qna.getContent())
                .regDate(qna.getRegDate())
                .modDate(qna.getModDate())
                .build();

        return qnaDTO;
    }
}
