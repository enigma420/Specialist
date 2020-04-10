package pl.specialist.searchexpert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist,Long> {

    @Override
    <S extends Specialist> S save(S s);

    @Override
    <S extends Specialist> List<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<Specialist> findById(Long aLong);

    @Override
    List<Specialist> findAll();

    @Override
    void delete(Specialist specialist);

    @Override
    void deleteAll();



    /*Find SPECIALIST by location*/
    Specialist findSpecialistByProvince(Province province);
    Specialist findSpecialistByCity(String city);
    Specialist findSpecialistByProvinceAndCity(Province province, String city);

    /*Find SPECIALIST by name and surname*/
    Specialist findSpecialistByNameAndSurname(String name,String surname);

    /*Find SPECIALIST by profession*/
    Specialist findSpecialistByProfession(String profession);

    /*MIXED*/
    /*Find SPECIALIST by location and profession*/
    Specialist findSpecialistByProvinceAndProfession(Province province, String profession);
    Specialist findSpecialistByCityAndProfession(String city, String profession);
    Specialist findSpecialistByProvinceAndCityAndProfession(Province province,String city,String profession);




    /*Find SPECIALISTS by location*/
    Iterable<Specialist> findSpecialistsByProvince(Province province);
    Iterable<Specialist> findSpecialistsByCity(String city);

    /*Find SPECIALISTS by profession*/
    Iterable<Specialist> findSpecialistsByProfession(String profession);
}