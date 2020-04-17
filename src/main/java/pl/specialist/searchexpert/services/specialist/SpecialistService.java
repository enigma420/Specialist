package pl.specialist.searchexpert.services.specialist;

import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import java.util.HashSet;
import java.util.List;

public interface SpecialistService {

    Specialist createSpecialistAccount(Specialist specialist);
    Specialist updateSpecialistAccount(Specialist specialist);
    void deleteSpecialistBySpecialistId(String specialistId);
    void deleteAllSpecialists();
    Specialist findSpecialistById(String specialistId);
    Specialist findSpecialistByMail(String mail);
    Iterable<Specialist> findAllSpecialists();
    Iterable<Specialist> findSpecialistsByPersonalIdentity(String name, String surname);
    HashSet<Specialist> findSpecialists(Province province, String city, List<String> profession);
}
