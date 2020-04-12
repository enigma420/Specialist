package pl.specialist.searchexpert.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
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
    <S extends Specialist> List<S> saveAll(Iterable<S> iterable);

    @Override
    List<Specialist> findAll();

    @Override
    void delete(Specialist specialist);

    @Override
    void deleteAll();

    Specialist findSpecialistById(Long id);

    /*Find SPECIALISTS by name and surname*/
    Iterable<Specialist> findSpecialistsByNameAndSurname(String name,String surname);

    /*Find SPECIALISTS by location*/
    Iterable<Specialist> findSpecialistsByProvince(Province province);
    Iterable<Specialist> findSpecialistsByCity(String city);
    Iterable<Specialist> findSpecialistsByProvinceAndCity(Province province, String city);

    /*Find SPECIALISTS by profession*/
    Iterable<Specialist> findSpecialistsByProfession(String profession);

    /*MIXED*/
    /*Find SPECIALIST by location and profession*/
    Iterable<Specialist> findSpecialistsByProvinceAndProfession(Province province, String profession);
    Iterable<Specialist> findSpecialistsByCityAndProfession(String city, String profession);
    Iterable<Specialist> findSpecialistsByProvinceAndCityAndProfession(Province province,String city,String profession);
}