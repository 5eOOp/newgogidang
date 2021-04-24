package me.seoop.newgogidang.service;

import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.QnaDTO;
import me.seoop.newgogidang.entity.Qna;

public interface QnaService {

    PageResultDTO<QnaDTO, Object[]> getList(PageRequestDTO requestDTO);
    QnaDTO getQna(Long qno);

    default Qna dtoToEntity(QnaDTO qnaDTO) {
        Qna qna = Qna.builder()
                .title(qnaDTO.getTitle())
                .content(qnaDTO.getContent())
                .build();

        return qna;
    }

    default QnaDTO entityToDTO(Qna qna) {
        QnaDTO qnaDTO = QnaDTO.builder()
                .title(qna.getTitle())
                .content(qna.getContent())
                .build();

        return qnaDTO;
    }
}
