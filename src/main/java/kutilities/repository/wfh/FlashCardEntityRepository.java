package kutilities.repository.wfh;

import kutilities.domain.entity.wfh.FlashCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardEntityRepository extends JpaRepository<FlashCardEntity, Long> {
    String TABLE = "flashcardentity";
}
