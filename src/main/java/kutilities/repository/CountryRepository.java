package kutilities.repository;

import kutilities.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Person,String> {

    String TABLE = "country";
}
