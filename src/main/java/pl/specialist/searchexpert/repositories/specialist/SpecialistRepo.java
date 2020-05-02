package pl.specialist.searchexpert.repositories.specialist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.HashSet;
import java.util.List;

@Repository
public interface SpecialistRepo extends JpaRepository<Specialist,Long> {

    Specialist findBySpecialistId(String id);
    Specialist findByMail(String mail);
    Specialist findByPhoneNumber(String phoneNumber);

    /*Find SPECIALISTS by personal identity*/
    @Query("SELECT c FROM Specialist c WHERE (:name is null or c.name = :name) and (:surname is null or c.surname = :surname)")
    HashSet<Specialist> findSpecialistsByNameAndSurname(@Param("name")String name,@Param("surname")String surname);

    /*Find SPECIALIST by location and profession*/
    @Query("SELECT c FROM Specialist c WHERE (:province is null or c.province = :province) and (:city is null or c.city = :city) and (:profession is null or c.profession = :profession)")
    HashSet<Specialist> findSpecialistsByProvinceAndCityAndProfession(@Param("province")Province province, @Param("city")String city, @Param("profession") String profession);
}