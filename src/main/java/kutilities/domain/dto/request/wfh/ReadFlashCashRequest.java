package kutilities.domain.dto.request.wfh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadFlashCashRequest {
    @JsonProperty("file_path")
    private String filePath;
}
