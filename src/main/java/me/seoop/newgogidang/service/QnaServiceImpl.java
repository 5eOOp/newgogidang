package me.seoop.newgogidang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.QnaDTO;
import me.seoop.newgogidang.entity.Qna;
import me.seoop.newgogidang.repository.QnaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaServiceImpl implements QnaService {

    private final QnaRepository qnaRepository;

    @Override
    public PageResultDTO<QnaDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("qno").descending());
        Page<Object[]> result = qnaRepository.getListPage(pageable);
        Function<Object[], QnaDTO> fn = (arr -> entityToDTO(
                (Qna) arr[0]
        ));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public QnaDTO getQna(Long qno) {
        Qna qna = qnaRepository.getOne(qno);
        QnaDTO qnaDTO = QnaDTO.builder()
                .qno(qna.getQno())
                .title(qna.getTitle())
                .content(qna.getContent())
                .build();
        return qnaDTO;
    }
}
