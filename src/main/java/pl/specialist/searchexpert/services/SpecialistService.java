package pl.specialist.searchexpert.services;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.SpecialistIdException;
import pl.specialist.searchexpert.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.SpecialistRepo;

import java.util.List;

@Service
public class SpecialistService {

    private final SpecialistRepo specialistRepo;

    public SpecialistService(SpecialistRepo specialistRepo) {
        this.specialistRepo = specialistRepo;
    }

    public Specialist createSpecialistAccount(Specialist specialist){
        return specialistRepo.save(specialist);
    }

    public Specialist updateSpecialistAccount(Specialist specialist){
        if(specialist.getSpecialistId() == null) throw new SpecialistNotFoundException("Cannot Update Specialist with ID: '" + specialist.getSpecialistId() + "' because doesn't exist");
        return specialistRepo.save(specialist);
    }

    public void deleteSpecialistBySpecialistId(String specialistId){

        if(specialistRepo.count() == 0) throw new SpecialistIdException("You cannot delete Specialist because doesn't exist");
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        if(specialist == null) throw new SpecialistIdException("Cannot Delete Specialist with ID: '" + specialistId + "' because doesn't exist");

        specialistRepo.delete(findSpecialistById(specialistId));
    }

    public void deleteAllSpecialists(){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        specialistRepo.deleteAll();
    }

    public Specialist findSpecialistById(String specialistId){
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);

        if(specialistRepo.count() == 0) throw new SpecialistIdException("No Specialist exist");
        else if(specialist == null) throw new SpecialistIdException("Specialist with ID: '" + specialistId + "' doesn't exist");

        return specialist;
    }

    public Specialist findSpecialistByMail(String mail){
        Specialist specialist = specialistRepo.findByMail(mail);

        if(specialistRepo.count() == 0) throw new SpecialistIdException("No Specialist exist");
        else if(specialist == null) throw new SpecialistIdException("Specialist with mail: '" + mail + "' doesn't exist");

        return specialist;
    }

    public Iterable<Specialist> findAllSpecialists(){
        return specialistRepo.findAll();
    }

    public Iterable<Specialist> findSpecialistsByPersonalIdentity(String name, String surname){
        if(specialistRepo.count() == 0) throw new SpecialistIdException("No Specialists exist");

        return specialistRepo.findSpecialistsByNameAndSurname(name,surname);
    }


    public Iterable<Specialist> findSpecialists(Province province, String city, String profession){
        if(specialistRepo.count() == 0) throw new SpecialistIdException("No Specialists exist");
//        if(province == null && city == null) return specialistRepo.findSpecialistsByProfession(profession.get(0));
//        else if(province == null) return  specialistRepo.findSpecialistsByCityAndProfession(city,profession.get(0));
//        else if(city == null) return  specialistRepo.findSpecialistsByProvinceAndProfession(province,profession.get(0));
//        else if(profession == null) return specialistRepo.findSpecialistsByProvinceAndCity(province,city);

        return specialistRepo.findSpecialistsByProvinceAndCityAndProfession(province,city,profession);
    }
}
