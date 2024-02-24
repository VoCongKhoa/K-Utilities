package kutilities.service.wfh;

import kutilities.domain.constants.wfh.WFHConstants;
import kutilities.domain.entity.wfh.FlashCardXMLEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class XMLService {

    public List<FlashCardXMLEntity> readXMLFile(String pathName) throws ParserConfigurationException, IOException, SAXException {
        File file = new File(pathName);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);
        List<FlashCardXMLEntity> flashCardXMLs = new ArrayList<>();
        for (int i = 0; i < document.getElementsByTagName(WFHConstants.TAG_NAME_CARD).getLength(); i++) {
            String id = document.getElementsByTagName(WFHConstants.TAG_NAME_CARD).item(i).getAttributes()
                    .getNamedItem(WFHConstants.ATTRIBUTE_NAME_ID).getTextContent();
            String category = document.getElementsByTagName(WFHConstants.TAG_NAME_CARD).item(i).getAttributes()
                    .getNamedItem(WFHConstants.ATTRIBUTE_NAME_CATEGORY).getTextContent();
            String frontAudio = document.getElementsByTagName(WFHConstants.TAG_NAME_TERMS).item(i).getAttributes()
                    .getNamedItem(WFHConstants.ATTRIBUTE_NAME_AUDIO).getTextContent();
            String frontValue = document.getElementsByTagName(WFHConstants.TAG_NAME_TERMS).item(i).getTextContent().trim();
            String backAudio = document.getElementsByTagName(WFHConstants.TAG_NAME_DEF).item(i).getAttributes()
                    .getNamedItem(WFHConstants.ATTRIBUTE_NAME_AUDIO).getTextContent();
            String backValue = document.getElementsByTagName(WFHConstants.TAG_NAME_DEF).item(i).getTextContent().trim();
            String module = document.getElementsByTagName(WFHConstants.TAG_NAME_SET).item(i).getTextContent().trim();
            flashCardXMLs.add(FlashCardXMLEntity.builder()
                    .flashCardIdXml(Long.valueOf(id))
                    .categoryXml(StringUtils.hasText(category) ? category : WFHConstants.DOUBLE_QUOTE_OPEN_CLOSE)
                    .frontAudioXml(frontAudio)
                    .frontValueXml(frontValue)
                    .backAudioXml(backAudio)
                    .backValueXml(backValue)
                    .moduleXml(StringUtils.hasText(module) ? module : WFHConstants.DOUBLE_QUOTE_OPEN_CLOSE)
                    .build());
        }
        return flashCardXMLs;
    }
}
