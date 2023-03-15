package kutilities.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kutilities.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = PersonRepository.TABLE)
public class Person {
    @Id
    @Column(nullable = false, name = "person_id")
    private String personId;
    @Column(name = "country_id")
    private String countryId;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "occupation")
    private String occupation;
    @Column(name = "experience")
    private int experience ;
    @Column(name = "age")
    private int age;
    @Column(name = "salary")
    private BigDecimal salary;

}
