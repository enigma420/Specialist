package pl.specialist.searchexpert.services.specialist;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerIdException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionNotFoundException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistIdException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.SpecialistRepo;
import pl.specialist.searchexpert.repositories.opinion.OpinionRepo;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class SpecialistServiceImpl implements SpecialistService{

    private final SpecialistRepo specialistRepo;

    private final OpinionRepo opinionRepo;

    public SpecialistServiceImpl(SpecialistRepo specialistRepo, OpinionRepo opinionRepo) {
        this.specialistRepo = specialistRepo;
        this.opinionRepo = opinionRepo;
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
        return specialistRepo.save(specialist);
    }

    /*Specialist*/
//    @Override
//    public Specialist markedByCustomer(Specialist specialist, Customer customer){
//
//        Set<Customer> groupOfCustomers = specialist.getMarks();
//        if(!groupOfCustomers.contains(customer)){
//            groupOfCustomers.add(customer);
//            specialist.setMarks(groupOfCustomers);
//        } else throw new SpecialistIdException("you have this customer in your favorites list");
//        return specialistRepo.save(specialist);
//    }


    @Override
    public Specialist updateSpecialistRate(Specialist specialist, Integer stars){

        Specialist existingSpecialist = specialistRepo.findBySpecialistId(specialist.getSpecialistId());
        if(existingSpecialist == null) throw new SpecialistNotFoundException("Cannot rate specialist with ID: '" + specialist.getSpecialistId() + "' because doesn't exist");
        else {
            specialist.setNumberOfRatings(specialist.getNumberOfRatings()+1);
            specialist.setRateStars((specialist.getRateStars()+stars)/(specialist.getNumberOfRatings()));
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
    public HashSet<Specialist> findSpecialistsByPersonalIdentity(String name, String surname){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialists isn't exist");
        HashSet<Specialist> groupOfSpecialists = specialistRepo.findSpecialistsByNameAndSurname(name,surname);
        if(groupOfSpecialists.size() == 0) throw new SpecialistNotFoundException("Any Specialist with the given data was not found");
        return groupOfSpecialists;
    }

    @Override
    public HashSet<Specialist> findSpecialists(Province province, String city, String profession){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        HashSet<Specialist> groupOfSpecialists = specialistRepo.findSpecialistsByProvinceAndCityAndProfession(province,city,profession);
        if(groupOfSpecialists.isEmpty()) throw new SpecialistNotFoundException("Any Specialist with this conditions was not found");
        return groupOfSpecialists;
    }

    @Override
    public HashSet<Opinion> findAllSpecialistOpinionsById(String specialistId){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialist isn't exist");
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        HashSet<Opinion> allSpecialistOpinions = opinionRepo.findOpinionsBySpecialist(specialist);
        if(allSpecialistOpinions.isEmpty()) throw new OpinionNotFoundException("Any Opinion isn't exist");
        return allSpecialistOpinions;
    }

}
