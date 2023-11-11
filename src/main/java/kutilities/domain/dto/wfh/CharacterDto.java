package kutilities.domain.dto.wfh;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDto {
    private String character;
    private Boolean isHeart;
}
