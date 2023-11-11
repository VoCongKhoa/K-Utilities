package kutilities.controller.excel;

import kutilities.domain.dto.request.excel.ReadExcelRequest;
import kutilities.domain.entity.excel.Product;
import kutilities.domain.entity.excel.SaleRecord;
import kutilities.service.excel.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static kutilities.utils.ExcelUtils.generateRandom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/products/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        List<Product> products = List.of(
                Product.builder()
                        .productId(generateRandom())
                        .productCode(generateRandom().substring(0, 8).toUpperCase())
                        .productName("a")
                        .price(10000.00)
                        .quantity(1)
                        .build(),
                Product.builder()
                        .productId(generateRandom())
                        .productCode(generateRandom().substring(0, 8).toUpperCase())
                        .productName("b")
                        .quantity(2)
                        .build(),
                Product.builder()
                        .productId(generateRandom())
                        .productCode(generateRandom().substring(0, 8).toUpperCase())
                        .productName("c")
                        .price(20000.00)
                        .quantity(3)
                        .build());
        excelService.writeProducts(response, products);
    }

    @PostMapping("/sale-records/read")
    public List<SaleRecord> readExcel(HttpServletResponse response, @RequestBody ReadExcelRequest request) throws IOException {
        return excelService.readSaleRecords(response, request.getFilePath());
    }

    @GetMapping("/sale-records/download")
    public void saleRecordsDownloadExcel(HttpServletResponse response) throws IOException {
        excelService.writeSaleRecords(response);
    }
}
