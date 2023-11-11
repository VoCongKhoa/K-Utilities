package kutilities.domain.dto.wfh;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BackValue {
    private String audio;
    private String value;
}
