package kutilities.domain.dto.wfh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFlashCardsRequest {
    @JsonProperty("file_path_excel")
    private String filePathExcel;

    @JsonProperty("file_path_xml")
    private String filePathXml;
}
