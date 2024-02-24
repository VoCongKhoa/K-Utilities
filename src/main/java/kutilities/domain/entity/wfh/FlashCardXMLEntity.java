package kutilities.domain.entity.wfh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlashCardXMLEntity {

    @Column(name = "flashCardIdXml")
    private Long flashCardIdXml;

    @Column(name = "frontValueOrigin")
    private String frontValueOrigin;

    @Column(name = "frontValueXml")
    private String frontValueXml;

    @Column(name = "frontAudioXml")
    private String frontAudioXml;

    @Column(name = "backValueXml")
    private String backValueXml;

    @Column(name = "backAudioXml")
    private String backAudioXml;

    @Column(name = "categoryXml")
    private String categoryXml;

    @Column(name = "moduleXml")
    private String moduleXml;
}
