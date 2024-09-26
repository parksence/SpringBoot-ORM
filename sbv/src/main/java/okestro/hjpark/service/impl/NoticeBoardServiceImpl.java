package okestro.hjpark.service.impl;

import okestro.hjpark.entity.NoticeBoardEntity;
import okestro.hjpark.repo.NoticeBoardRepository;
import okestro.hjpark.service.NoticeBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Override
    public List<NoticeBoardEntity> boardList() {
        return noticeBoardRepository.findAll();
    }

    @Override
    public NoticeBoardEntity board(Long id) {
        return noticeBoardRepository.findById(id).orElse(null);
    }
}
