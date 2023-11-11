package kutilities.service.excel;

import kutilities.domain.constants.excel.ExcelConstants;
import kutilities.domain.entity.excel.Product;
import kutilities.domain.entity.excel.SaleRecord;
import kutilities.domain.enums.excel.ContentForProductsExcelFile;
import kutilities.domain.enums.excel.ContentForSaleRecordsExcelFile;
import kutilities.repository.excel.SaleRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static kutilities.utils.ExcelUtils.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExcelService {

    public final SaleRecordRepository saleRecordRepository;

    @Transactional
    public List<SaleRecord> readSaleRecords(HttpServletResponse response, String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        List<SaleRecord> saleRecords = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            saleRecords.add(SaleRecord.buildOne(row));
        }
        saleRecordRepository.saveAll(saleRecords);
        return saleRecords;
    }

    public void writeProducts(HttpServletResponse response, List<Product> products) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sale Records");
        List<String> headers = ExcelConstants.PRODUCT_HEADERS;
        CellStyle headerStyle = setupStyle("Arial", workbook.createCellStyle(), workbook.createFont(),
                IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND, (short) 16, true, true);
        CellStyle contentStyle = setupStyle("Arial", workbook.createCellStyle(), workbook.createFont(),
                IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, (short) 14, false, false);

        writeHeader(sheet, headers, headerStyle);
        this.writeContentForProducts(sheet, headers, products, contentStyle);
        setupColumnWidth(sheet, headers);

        // Set response headers for Excel file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");

        // Write workbook to response stream
        workbook.write(response.getOutputStream());
        workbook.close();


    }

    public void writeSaleRecords(HttpServletResponse response) throws IOException {
        List<SaleRecord> saleRecords = saleRecordRepository.findAll();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sale Records");
        List<String> headers = ExcelConstants.SALE_RECORDS_HEADERS;
        CellStyle headerStyle = setupStyle("Arial", workbook.createCellStyle(), workbook.createFont(),
                IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND, (short) 16, true, true);
        CellStyle contentStyle = setupStyle("Arial", workbook.createCellStyle(), workbook.createFont(),
                IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, (short) 14, false, false);

        writeHeader(sheet, headers, headerStyle);
        this.writeContentForSaleRecords(sheet, headers, saleRecords, contentStyle);
//        this.setupColumnWidth(sheet, headers);

        // Set response headers for Excel file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=Sale Records Update.xlsx");

        // Write workbook to response stream
        workbook.write(response.getOutputStream());
        workbook.close();


    }

    private void writeContentForProducts(Sheet sheet, List<String> headers, List<Product> products, CellStyle contentStyle) {
        AtomicInteger rowIdx = new AtomicInteger(1);
        products.forEach(p -> {
            Row row = sheet.createRow(rowIdx.intValue());
            AtomicInteger cellIdx = new AtomicInteger(0);
            headers.forEach(h -> {
                Cell cell = row.createCell(cellIdx.intValue());
                cell.setCellStyle(contentStyle);
                cell.setCellValue(ContentForProductsExcelFile.getByIdx(cellIdx.intValue()).execute(rowIdx.intValue(), p));
                cellIdx.getAndIncrement();
            });
            rowIdx.getAndIncrement();
        });
    }

    private void writeContentForSaleRecords(Sheet sheet, List<String> headers, List<SaleRecord> saleRecords, CellStyle contentStyle) {
        AtomicInteger rowIdx = new AtomicInteger(1);
        saleRecords.forEach(s -> {
            Row row = sheet.createRow(rowIdx.intValue());
            AtomicInteger cellIdx = new AtomicInteger(0);
            headers.forEach(h -> {
                Cell cell = row.createCell(cellIdx.intValue());
                cell.setCellStyle(contentStyle);
                cell.setCellValue(ContentForSaleRecordsExcelFile.getByIdx(cellIdx.intValue()).execute(rowIdx.intValue(), s));
                cellIdx.getAndIncrement();
            });
            rowIdx.getAndIncrement();
        });
    }
}
