package okestro.hjpark.service;

import okestro.hjpark.entity.NoticeBoardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeBoardService {
    List<NoticeBoardEntity> boardList();
    NoticeBoardEntity board(Long id);
    NoticeBoardEntity addBoard(NoticeBoardEntity board);
    NoticeBoardEntity updateBoard(NoticeBoardEntity board);
    void deleteBoard(Long id);
}
