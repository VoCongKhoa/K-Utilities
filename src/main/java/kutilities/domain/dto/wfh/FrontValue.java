package kutilities.domain.dto.wfh;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontValue {
    private String audio;
    private List<CharacterDto> value;
}
