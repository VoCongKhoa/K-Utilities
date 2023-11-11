package kutilities.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static kutilities.domain.constants.excel.ExcelConstants.DASH;

@UtilityClass
public class ExcelUtils {

    public static String generateRandom() {
        return UUID.randomUUID().toString().replace("-", Strings.EMPTY);
    }

    public static String getCellValue(Cell cell) {
        String cellValue = Strings.EMPTY;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        Date dateValue = cell.getDateCellValue();
                        LocalDateTime dateTimeValue = DateUtil.toLocalDateTime(dateValue);
                        cellValue = dateTimeValue.toString();
                    } else {
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {
                            // Number does not include decimal part
                            long longValue = (long) numericValue;
                            cellValue = String.valueOf(longValue);
                        } else {
                            // Number includes decimal part
                            cellValue = String.valueOf(numericValue);
                        }
                    }
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = cell.getCellFormula();
                    break;
                case BLANK:
                    cellValue = "";
                    break;
                default:
                    // Handle other cell types as needed
                    break;
            }
        }
        return cellValue;
    }

    public static BigDecimal toBigDecimal(Row row, int i) {
        return new BigDecimal(getCellValue(row.getCell(i))).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isBlankCell(String cellValue) {
        return StringUtils.isBlank(cellValue) || DASH.equals(cellValue);
    }

    public static void setupColumnWidth(Sheet sheet, List<String> headers) {
        AtomicInteger colIdx = new AtomicInteger(0);
        headers.forEach(s -> {
            sheet.autoSizeColumn(colIdx.intValue());
            colIdx.getAndIncrement();
        });
    }

    public static CellStyle setupStyle(String fontName, CellStyle cellStyle, Font font, IndexedColors color, FillPatternType fillPatternType, short fontHeight, boolean isBold, boolean isHeader) {
        if (isHeader) {
            cellStyle.setFillForegroundColor(color.getIndex());
            cellStyle.setFillPattern(fillPatternType);
        }
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontHeight);
        font.setBold(isBold);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public static void writeHeader(Sheet sheet, List<String> headers, CellStyle headerStyle) {
        AtomicInteger rowIdx = new AtomicInteger(0);
        Row row = sheet.createRow(rowIdx.intValue());
        headers.forEach(s -> {
            Cell cell = row.createCell(rowIdx.intValue());
            if (headerStyle != null) {
                cell.setCellStyle(headerStyle);
            }
            cell.setCellValue(headers.get(rowIdx.intValue()));
            rowIdx.getAndIncrement();
        });
    }
}
