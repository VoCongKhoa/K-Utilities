package kutilities.controller.wfh;

import kutilities.domain.dto.request.excel.ReadExcelRequest;
import kutilities.domain.dto.request.wfh.ReadFlashCashRequest;
import kutilities.domain.entity.excel.SaleRecord;
import kutilities.domain.entity.wfh.FlashCardEntity;
import kutilities.service.wfh.WFHService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wfh")
public class WFHController {

    public final WFHService wfhService;

    @PostMapping("/flash-card-content/read")
    public List<FlashCardEntity> readExcel(HttpServletResponse response, ReadFlashCashRequest request) throws IOException {
        return wfhService.readFlashCards(response, request);
    }
}
