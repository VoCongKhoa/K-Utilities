package kutilities.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import kutilities.dto.PersonCountryCombination;
import kutilities.entity.Country;
import kutilities.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class CodesLibraryService {

    static List<Person> personList;
    static List<Country> countryList;

    @Value("${infor.app.name}")
    public static String projectName;

    static {
        countryList = List.of(Country.builder()
                        .countryId(UUID.randomUUID().toString())
                        .country("Thailand")
                        .countryCode("TH")
                        .build(),
                Country.builder()
                        .countryId(UUID.randomUUID().toString())
                        .country("China")
                        .countryCode("CN")
                        .build(),
                Country.builder()
                        .countryId(UUID.randomUUID().toString())
                        .country("Indonesia")
                        .countryCode("ID")
                        .build(),
                Country.builder()
                        .countryId(UUID.randomUUID().toString())
                        .country("United States")
                        .countryCode("US")
                        .build(),
                Country.builder()
                        .countryId(UUID.randomUUID().toString())
                        .country("Vietnam")
                        .countryCode("VN")
                        .build(),
                Country.builder()
                        .countryId(UUID.randomUUID().toString())
                        .country("Russia")
                        .countryCode("RU")
                        .build()

        );

        personList = List.of(Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(3).getCountryId())
                        .name("Klausewitz")
                        .gender("Male")
                        .age(52)
                        .occupation("Accountant")
                        .experience(10)
                        .salary(new BigDecimal("9984.14"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(4).getCountryId())
                        .name("Galier")
                        .gender("Male")
                        .age(33)
                        .occupation("Analyst")
                        .experience(5)
                        .salary(new BigDecimal("4898.36"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(5).getCountryId())
                        .name("Fass")
                        .gender("Male")
                        .age(43)
                        .occupation("Accountant")
                        .experience(7)
                        .salary(new BigDecimal("1415.68"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(1).getCountryId())
                        .name("Keely")
                        .gender("Male")
                        .age(26)
                        .occupation("Analyst")
                        .experience(2)
                        .salary(new BigDecimal("1300.75"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(2).getCountryId())
                        .name("Murtagh")
                        .gender("Female")
                        .age(53)
                        .occupation("Engineer")
                        .experience(15)
                        .salary(new BigDecimal("6300.75"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(2).getCountryId())
                        .name("Grene")
                        .gender("Female")
                        .age(52)
                        .occupation("Nurse")
                        .experience(20)
                        .salary(new BigDecimal("1985.42"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(1).getCountryId())
                        .name("Kikke")
                        .gender("Male")
                        .age(45)
                        .occupation("Engineer")
                        .experience(15)
                        .salary(new BigDecimal("3972.14"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(5).getCountryId())
                        .name("Shopcott")
                        .gender("Male")
                        .age(46)
                        .occupation("Professor")
                        .experience(20)
                        .salary(new BigDecimal("4827.17"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(3).getCountryId())
                        .name("Yarnton")
                        .gender("Female")
                        .age(36)
                        .occupation("Professor")
                        .experience(9)
                        .salary(new BigDecimal("3312.90"))
                        .build(),
                Person.builder()
                        .personId(UUID.randomUUID().toString())
                        .countryId(countryList.get(4).getCountryId())
                        .name("Kleszinski")
                        .gender("Female")
                        .age(47)
                        .occupation("Engineer")
                        .experience(8)
                        .salary(new BigDecimal("1260.03"))
                        .build()
        );

    }

    public static void main(String[] args) throws JsonProcessingException {

        /**
         * Convert List to Map
         *
         */
        Map<String, Person> exampleMap = personList.stream().collect(Collectors.toMap(Person::getPersonId, person -> person));
//        var mapper = new ObjectMapper();
//        JsonNode jsonNode = mapper.readTree(exampleMap.toString());
        log.info(exampleMap.toString());

        /**
         * Convert List to Map, groupingBy
         *
         */
        // GroubpingBy gender
        Map<String, List<Person>> personGroupingByGenderMap = personList.stream().collect(Collectors.groupingBy(Person::getGender));
        log.info(personGroupingByGenderMap.toString());
        // GroupingBy gender then groupingBy occupation
        Map<String, Map<String, List<Person>>> personGroupingByGenderThenOccupationMap = personList.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.groupingBy(Person::getOccupation)));
        log.info(personGroupingByGenderThenOccupationMap.toString());
        // GroupingBy gender then groupingBy occupation => counting
        Map<String, Map<String, Long>> personGroupingByGenderThenOccupationThenCoutingMap = personList.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.groupingBy(Person::getOccupation, Collectors.counting())));
        log.info(personGroupingByGenderThenOccupationThenCoutingMap.toString());

        /**
         * Combine 2 list to 1 list
         *
         */
        // 1/ Convert list to map
        Map<String, Person> personMap = personList.stream().collect(Collectors.toMap(Person::getPersonId, person -> person));
        Map<String, Country> countryMap = countryList.stream().collect(Collectors.toMap(Country::getCountryId, country -> country));
        // 2/ Combine 2 map to 1 combinedList
        List<PersonCountryCombination> personCountryCombinationList = personMap.entrySet().stream().map(
                personEntry -> {
                    Country country = countryMap.getOrDefault(personEntry.getKey(), new Country());
                    return PersonCountryCombination.builder()
                            .personId(personEntry.getValue().getPersonId())
                            .name(personEntry.getValue().getName())
                            .gender(personEntry.getValue().getGender())
                            .occupation(personEntry.getValue().getOccupation())
                            .countryCode(country.getCountryCode())
                            .build();
                }
        ).toList();
        log.info(personCountryCombinationList.toString());

        log.info(projectName);
    }
}
