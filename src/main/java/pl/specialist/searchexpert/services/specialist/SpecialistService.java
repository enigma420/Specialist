package pl.specialist.searchexpert.services.specialist;

import org.springframework.web.multipart.MultipartFile;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.io.IOException;
import java.util.HashSet;

public interface SpecialistService {

    Specialist uploadProfileImg(MultipartFile file, String specialistId) throws IOException;

    Specialist updateSpecialistAccount(Specialist specialist);
    void deleteSpecialistBySpecialistId(String specialistId);
    Specialist findSpecialistById(String specialistId);
    Specialist findSpecialistByMail(String mail);
    Iterable<Specialist> findAllSpecialists();
    Iterable<Specialist> findSpecialistsByPersonalIdentity(String name, String surname);
    HashSet<Specialist> findSpecialistsByProfessionAndLocation(Province province, String city, String profession);
    Specialist updateSpecialistRate(Specialist specialist, Integer stars);
//    Specialist markedByCustomer(Specialist specialist, Customer customer);
HashSet<Opinion> findAllSpecialistOpinionsById(String specialistId);
}
