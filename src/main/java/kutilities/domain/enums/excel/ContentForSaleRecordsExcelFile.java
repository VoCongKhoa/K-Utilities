package kutilities.domain.enums.excel;

import kutilities.domain.entity.excel.SaleRecord;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import static kutilities.domain.constants.excel.ExcelConstants.DASH;
import static kutilities.utils.DateTimeUtils.formatLocalDateTime;

public enum ContentForSaleRecordsExcelFile {

    NO(0, (cellIdx, p) -> String.valueOf(cellIdx)),
    ID(1, (cellIdx, p) -> StringUtils.hasText(p.getId()) ? p.getId() : DASH),
    CODE(2, (cellIdx, p) -> StringUtils.hasText(p.getCode()) ? p.getCode() : DASH),
    REGION(3, (cellIdx, p) -> StringUtils.hasText(p.getRegion()) ? p.getRegion() : DASH),
    ITEM_TYPE(4, (cellIdx, p) -> StringUtils.hasText(p.getItemType()) ? p.getItemType() : DASH),
    SALE_CHANNEL(5, (cellIdx, p) -> StringUtils.hasText(p.getSalesChannel()) ? p.getSalesChannel() : DASH),
    ORDER_PRIORITY(6, (cellIdx, p) -> StringUtils.hasText(p.getOrderPriority()) ? p.getOrderPriority() : DASH),
    ORDER_DATE(7, (cellIdx, p) -> Objects.nonNull(p.getOrderDate()) ? formatLocalDateTime(String.valueOf(p.getOrderDate()), "yyyy-MM-dd'T'HH:mm", "dd-MMM-yyyy HH:mm", Locale.US, Locale.US) : DASH),
    ORDER_ID(8, (cellIdx, p) -> StringUtils.hasText(p.getOrderId()) ? p.getOrderId() : DASH),
    SHIP_DATE(9, (cellIdx, p) -> Objects.nonNull(p.getShipDate()) ? formatLocalDateTime(String.valueOf(p.getShipDate()), "yyyy-MM-dd'T'HH:mm", "dd-MMM-yyyy HH:mm", Locale.US, Locale.US) : DASH),
    UNITS_SOLD(10, (cellIdx, p) -> Objects.nonNull(p.getUnitsSold()) ? String.valueOf(p.getUnitsSold()) : DASH),
    UNIT_PRICE(11, (cellIdx, p) -> Objects.nonNull(p.getUnitPrice()) ? String.valueOf(p.getUnitPrice()) : DASH),
    UNIT_COST(12, (cellIdx, p) -> Objects.nonNull(p.getUnitCost()) ? String.valueOf(p.getUnitCost()) : DASH),
    TOTAL_REVENUE(13, (cellIdx, p) -> Objects.nonNull(p.getTotalRevenue()) ? String.valueOf(p.getTotalRevenue()) : DASH),
    TOTAL_COST(14, (cellIdx, p) -> Objects.nonNull(p.getTotalCost()) ? String.valueOf(p.getTotalCost()) : DASH),
    TOTAL_PROFIT(15, (cellIdx, p) -> Objects.nonNull(p.getTotalProfit()) ? String.valueOf(p.getTotalProfit()) : DASH),
    ;

    private final int idx;
    private final ContentStrategy contentStrategy;


    ContentForSaleRecordsExcelFile(int idx, ContentStrategy contentStrategy) {
        this.idx = idx;
        this.contentStrategy = contentStrategy;
    }

    public static ContentForSaleRecordsExcelFile getByIdx(int idx) {
        return Arrays.stream(ContentForSaleRecordsExcelFile.values())
                .filter(c -> idx == c.idx)
                .findFirst()
                .orElseThrow();
    }

    public String execute(int cellIdx, SaleRecord s) {
        return contentStrategy.executeContent(cellIdx, s);
    }

    interface ContentStrategy {
        String executeContent(int cellIdx, SaleRecord s);
    }
}
