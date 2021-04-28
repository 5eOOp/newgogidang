package me.seoop.newgogidang.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.seoop.newgogidang.dto.NoticeDTO;
import me.seoop.newgogidang.dto.PageRequestDTO;
import me.seoop.newgogidang.dto.PageResultDTO;
import me.seoop.newgogidang.entity.Notice;
import me.seoop.newgogidang.entity.QNotice;
import me.seoop.newgogidang.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public PageResultDTO<NoticeDTO, Notice> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("Nno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO);
        Page<Notice> result = noticeRepository.findAll(booleanBuilder, pageable);
        Function<Notice, NoticeDTO> fn = (notice -> entityToDTO(notice));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public NoticeDTO getNotice(Long nno) {
        Notice notice = noticeRepository.getOne(nno);
        NoticeDTO noticeDTO = entityToDTO(notice);
        return noticeDTO;
    }

    @Override
    public void remove(Long nno) {
        noticeRepository.deleteById(nno);
    }

    @Override
    public void modify(NoticeDTO dto) {
        Optional<Notice> result = noticeRepository.findById(dto.getNno());
        if (result.isPresent()) {
            Notice notice = result.get();
            notice.changeTitle(dto.getTitle());
            notice.changeContent(dto.getContent());
            noticeRepository.save(notice);
        }
    }

    @Override
    public Long register(NoticeDTO dto) {
        log.info("register notice.....");
        log.info("notice dto: " + dto);
        Notice notice = dtoToEntity(dto);
        log.info("notice: " + notice);
        noticeRepository.save(notice);

        return notice.getNno();
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QNotice qNotice = QNotice.notice;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qNotice.nno.gt(0L);
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) {
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        BooleanBuilder condtionBuilder = new BooleanBuilder();

        if(type.contains("t")) {
            condtionBuilder.or(qNotice.title.contains(keyword));
        }
        if (type.contains("c")) {
            condtionBuilder.or(qNotice.content.contains(keyword));
        }

        booleanBuilder.and(condtionBuilder);

        return booleanBuilder;
    }
}
