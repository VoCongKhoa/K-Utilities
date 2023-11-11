package kutilities.domain.entity.excel;

import kutilities.domain.enums.excel.ProductType;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productId;
    private String productName;
    private String productCode;
    private Integer quantity;
    private Double price;
    private ProductType productType;
}
