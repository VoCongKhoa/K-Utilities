package kutilities.domain.entity.wfh;

import kutilities.repository.wfh.FlashCardEntityRepository;
import lombok.*;
import org.apache.poi.ss.usermodel.Row;

import javax.persistence.*;

@Entity
@Table(name = FlashCardEntityRepository.TABLE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flashcardid")
    private long flashCardId;
    @Column(name = "frontvalue")
    private String frontValue;
    @Column(name = "backvalue")
    private String backValue;
    @Column(name = "targetskillname")
    private String targetSkillName;
    @Column(name = "programid")
    private Long programId;

    public static FlashCardEntity buildOne(Row row) {
        return null;
    }
}
