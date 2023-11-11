package kutilities.domain.dto.request.excel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadExcelRequest {
    @JsonProperty("file_path")
    private String filePath;
}
