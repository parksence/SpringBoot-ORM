package okestro.hjpark.repo;

import okestro.hjpark.entity.NoticeBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoardEntity, Long> {
    List<NoticeBoardEntity> findAll();
}
