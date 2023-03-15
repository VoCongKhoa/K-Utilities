package kutilities.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String personId;
    private String countryId;
    private String name;
    private String gender;
    private String occupation;
    private int experience ;
    private int age;
    private BigDecimal salary;

}
