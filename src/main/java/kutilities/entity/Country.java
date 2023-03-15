package kutilities.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import kutilities.repository.CountryRepository;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = CountryRepository.TABLE)
public class Country {
    @Id
    @Column(nullable = false, name = "country_id")
    private String countryId;
    @Column(name = "country")
    private String country;
    @Column(name = "country_code")
    private String countryCode;
}
