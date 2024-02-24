package kutilities.domain.dto.wfh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTO {
    private int id;
    @JsonProperty("char")
    private String character;
    private Boolean isHeart;
}
