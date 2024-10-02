package okestro.hjpark.service;

import okestro.hjpark.entity.NoticeBoardEntity;

import java.util.List;

public interface NoticeBoardService {
    List<NoticeBoardEntity> boardList();
    NoticeBoardEntity board(Long id);
    NoticeBoardEntity addBoard(NoticeBoardEntity board);
    NoticeBoardEntity updateBoard(NoticeBoardEntity board);
    void deleteBoard(Long id);
    boolean verifyPassword(Long postId, String password);
}
