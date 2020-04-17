package pl.specialist.searchexpert.services;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.SpecialistIdException;
import pl.specialist.searchexpert.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.SpecialistRepo;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class SpecialistServiceImpl implements SpecialistService{

    private final SpecialistRepo specialistRepo;

    public SpecialistServiceImpl(SpecialistRepo specialistRepo) {
        this.specialistRepo = specialistRepo;
    }
    @Override
    public Specialist createSpecialistAccount(Specialist specialist){

            Specialist existingSpecialist = specialistRepo.findByMail(specialist.getMail());
            if(existingSpecialist != null) throw new SpecialistIdException("Account with this email: '" + specialist.getMail() + "' already exists");

        return specialistRepo.save(specialist);
    }
    @Override
    public Specialist updateSpecialistAccount(Specialist specialist){
        Specialist existingSpecialist = specialistRepo.findByMail(specialist.getMail());
        if(existingSpecialist.getSpecialistId() == null || existingSpecialist.getMail() == null) throw new SpecialistNotFoundException("Cannot Update Specialist with email: '" + specialist.getMail() + "' doesn't exist");
        else{
            if(!specialist.getSpecialistId().equals(existingSpecialist.getSpecialistId())) throw new SpecialistIdException("Cannot change SpecialistId");
            if(!specialist.getMail().equals(existingSpecialist.getMail())) throw new SpecialistIdException("Cannot change email address");
            specialist.setSpecialistId(specialistRepo.findBySpecialistId(specialist.getSpecialistId()).getSpecialistId());
            specialist.setMail(specialistRepo.findByMail(specialist.getMail()).getMail());
        }
        return specialistRepo.save(specialist);
    }
    @Override
    public void deleteSpecialistBySpecialistId(String specialistId){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("You cannot delete Specialist because doesn't exist");
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        if(specialist == null) throw new SpecialistIdException("Cannot Delete Specialist with ID: '" + specialistId + "' because doesn't exist");

        specialistRepo.delete(findSpecialistById(specialistId));
    }
    @Override
    public void deleteAllSpecialists(){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        specialistRepo.deleteAll();
    }
    @Override
    public Specialist findSpecialistById(String specialistId){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);

        if(specialist == null) throw new SpecialistIdException("Specialist with ID: '" + specialistId + "' doesn't exist");
        return specialist;
    }
    @Override
    public Specialist findSpecialistByMail(String mail){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        Specialist specialist = specialistRepo.findByMail(mail);
        if(specialist == null) throw new SpecialistIdException("Specialist with email: '" + mail + "' doesn't exist");
        return specialist;
    }
    @Override
    public Iterable<Specialist> findAllSpecialists(){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialists isn't exist");
        return specialistRepo.findAll();
    }
    @Override
    public Iterable<Specialist> findSpecialistsByPersonalIdentity(String name, String surname){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialists isn't exist");
        Iterable<Specialist> groupOfSpecialists = specialistRepo.findSpecialistsByNameAndSurname(name,surname);
        if(StreamSupport.stream(groupOfSpecialists.spliterator(),false).count() == 0) throw new SpecialistNotFoundException("Any Specialist with the given data was not found");
        return groupOfSpecialists;
    }

    @Override
    public HashSet<Specialist> findSpecialists(Province province, String city, List<String> profession){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        return specialistRepo.findSpecialistsByProvinceAndCityAndProfessionsIn(province,city,profession);
    }
}
