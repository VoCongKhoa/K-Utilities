package kutilities.domain.enums.excel;

import kutilities.domain.entity.excel.Product;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;

import static kutilities.domain.constants.excel.ExcelConstants.DASH;

public enum ContentForProductsExcelFile {

    NO(0, (cellIdx, p) -> String.valueOf(cellIdx)),
    ID(1, (cellIdx, p) -> StringUtils.hasText(p.getProductId()) ? p.getProductId() : DASH),
    NAME(2, (cellIdx, p) -> StringUtils.hasText(p.getProductName()) ? p.getProductName() : DASH),
    CODE(3, (cellIdx, p) -> StringUtils.hasText(p.getProductCode()) ? p.getProductCode() : DASH),
    QUANTITY(4, (cellIdx, p) -> Objects.nonNull(p.getQuantity()) ? String.valueOf(p.getQuantity()) : DASH),
    PRICE(5, (cellIdx, p) -> Objects.nonNull(p.getPrice()) ? String.valueOf(p.getPrice()) : DASH),
    ;

    private final int idx;
    private final ContentStrategy contentStrategy;


    ContentForProductsExcelFile(int idx, ContentStrategy contentStrategy) {
        this.idx = idx;
        this.contentStrategy = contentStrategy;
    }

    public static ContentForProductsExcelFile getByIdx(int idx) {
        return Arrays.stream(ContentForProductsExcelFile.values())
                .filter(c -> idx == c.idx)
                .findFirst()
                .orElseThrow();
    }

    public String execute(int cellIdx, Product p) {
        return contentStrategy.executeContent(cellIdx, p);
    }

    interface ContentStrategy {
        String executeContent(int cellIdx, Product p);
    }
}
