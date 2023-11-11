package kutilities.domain.entity.excel;

import kutilities.repository.excel.SaleRecordRepository;
import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Row;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

import static kutilities.domain.constants.LocalDateTimeConstants.YYYY_MM_DD_T_HH_MM;
import static kutilities.utils.DateTimeUtils.parseLocalDateTime;
import static kutilities.utils.ExcelUtils.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = SaleRecordRepository.TABLE)
public class SaleRecord {
    @Id
    private String id;
    private String code;
    private String region;
    private String country;
    private String itemType;
    private String salesChannel;
    private String orderPriority;
    private LocalDateTime orderDate; // MM/dd/yyyy
    private String orderId;
    private LocalDateTime shipDate; // MM/dd/yyyy
    private Long unitsSold;
    private BigDecimal unitPrice; // 0.00
    private BigDecimal unitCost; // 0.00
    private BigDecimal totalRevenue; // 0.00
    private BigDecimal totalCost; // 0.00
    private BigDecimal totalProfit; // 0.00

    public static SaleRecord buildOne(Row row) {
        return SaleRecord.builder()
                .id(generateRandom().substring(0, 16))
                .code(generateRandom().substring(0, 8).toUpperCase())
                .region(isBlankCell(getCellValue(row.getCell(0))) ? Strings.EMPTY : getCellValue(row.getCell(0)))
                .country(isBlankCell(getCellValue(row.getCell(1))) ? Strings.EMPTY : getCellValue(row.getCell(1)))
                .itemType(isBlankCell(getCellValue(row.getCell(2))) ? Strings.EMPTY : getCellValue(row.getCell(2)))
                .salesChannel(isBlankCell(getCellValue(row.getCell(3))) ? Strings.EMPTY : getCellValue(row.getCell(3)))
                .orderPriority(isBlankCell(getCellValue(row.getCell(4))) ? Strings.EMPTY : getCellValue(row.getCell(4)))
                .orderDate(isBlankCell(getCellValue(row.getCell(5))) ?
                        null : parseLocalDateTime(getCellValue(row.getCell(5)), YYYY_MM_DD_T_HH_MM, Locale.US))
                .orderId(isBlankCell(getCellValue(row.getCell(6))) ?
                        Strings.EMPTY : String.valueOf(Long.valueOf(getCellValue(row.getCell(6)))))
                .shipDate(isBlankCell(getCellValue(row.getCell(7))) ?
                        null : parseLocalDateTime(getCellValue(row.getCell(7)), YYYY_MM_DD_T_HH_MM, Locale.US))
                .unitsSold(isBlankCell(getCellValue(row.getCell(8))) ? null : Long.valueOf(getCellValue(row.getCell(8))))
                .unitPrice(isBlankCell(getCellValue(row.getCell(9))) ? null : toBigDecimal(row, 9))
                .unitCost(isBlankCell(getCellValue(row.getCell(10))) ? null : toBigDecimal(row, 10))
                .totalRevenue(isBlankCell(getCellValue(row.getCell(11))) ? null : toBigDecimal(row, 11))
                .totalCost(isBlankCell(getCellValue(row.getCell(12))) ? null : toBigDecimal(row, 12))
                .totalProfit(isBlankCell(getCellValue(row.getCell(13))) ? null : toBigDecimal(row, 13))
                .build();
    }
}
