package me.seoop.newgogidang.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.dto.QnaDTO;
import me.seoop.newgogidang.entity.QNotice;
import me.seoop.newgogidang.entity.QQna;
import me.seoop.newgogidang.entity.Qna;
import me.seoop.newgogidang.repository.QnaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class QnaServiceImpl implements QnaService {

    private final QnaRepository qnaRepository;

    @Override
    public PageResultDTO<QnaDTO, Qna> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("qno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Qna> result = qnaRepository.findAll(booleanBuilder, pageable);
        Function<Qna, QnaDTO> fn = (Qna -> entityToDTO(Qna));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public QnaDTO getQna(Long qno) {
        Qna qna = qnaRepository.getOne(qno);
        QnaDTO qnaDTO = entityToDTO(qna);
        return qnaDTO;
    }

    @Override
    public void remove(Long qno) {
        qnaRepository.deleteById(qno);
    }

    @Override
    public void modify(QnaDTO dto) {
        Optional<Qna> result = qnaRepository.findById(dto.getQno());
        if(result.isPresent()) {
            Qna qna = result.get();
            qna.changeTitle(dto.getTitle());
            qna.changeContent(dto.getContent());
            qnaRepository.save(qna);
        }
    }

    @Override
    public Long register(QnaDTO dto) {
        log.info("register qna.....");
        log.info("qna dto: " + dto);
        Qna qna = dtoToEntity(dto);
        log.info("qna: " + qna);
        qnaRepository.save(qna);

        return qna.getQno();
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QQna qQna = QQna.qna;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qQna.qno.gt(0L);
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        BooleanBuilder condtionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            condtionBuilder.or(qQna.title.contains(keyword));
        }
        if (type.contains("c")) {
            condtionBuilder.or(qQna.content.contains(keyword));
        }

        booleanBuilder.and(condtionBuilder);

        return booleanBuilder;
    }
}
