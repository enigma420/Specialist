package pl.specialist.searchexpert.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.List;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist,Long> {

    @Override
    long count();

    @Override
    <S extends Specialist> S save(S s);

    @Override
    List<Specialist> findAll();

    @Override
    void delete(Specialist specialist);

    Specialist findBySpecialistId(String id);
    Specialist findByMail(String mail);

    /*Find SPECIALISTS by personal identity*/
    @Query("SELECT c FROM Specialist c WHERE (:name is null or c.name = :name) and (:surname is null or c.surname = :surname)")
    Iterable<Specialist> findSpecialistsByNameAndSurname(String name,String surname);

    /*Find SPECIALIST by location and profession*/
    @Query("SELECT c FROM Specialist c WHERE (:province is null or c.province = :province) and (:city is null or c.city = :city) and(:profession is null or c.profession = :profession)")
    Iterable<Specialist> findSpecialistsByProvinceAndCityAndProfession(@Param("province")Province province,@Param("city")String city,@Param("profession")String profession);
}