package kutilities.domain.dto.wfh;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {
    private String word;
    private Boolean isFlagged;
}
