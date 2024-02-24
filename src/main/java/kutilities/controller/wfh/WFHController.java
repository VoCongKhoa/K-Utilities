package kutilities.controller.wfh;

import kutilities.domain.dto.wfh.GetAllFlashCardsRequest;
import kutilities.domain.entity.wfh.FlashCardEntity;
import kutilities.service.wfh.WFHService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wfh")
public class WFHController {

    public final WFHService wfhService;

    @PostMapping("/flash-card/get-all-as-list")
    public List<FlashCardEntity> getAllFlashCardsFromExcelFile(HttpServletResponse response, @RequestBody GetAllFlashCardsRequest request) {
        return wfhService.getAllFlashCardsAsList(response, request);
    }

    @PostMapping("/flash-card/get-all-as-html")
    public String getAllFlashCardsHTMLFromExcelFile(HttpServletResponse response, @RequestBody GetAllFlashCardsRequest request) throws ParserConfigurationException, IOException, SAXException {
        return wfhService.getAllFlashCardsAsHTML(response, request);
    }
}
