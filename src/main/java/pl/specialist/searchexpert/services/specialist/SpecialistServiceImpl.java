package pl.specialist.searchexpert.services.specialist;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionNotFoundException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistIdException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;
import pl.specialist.searchexpert.repositories.opinion.OpinionRepo;
import pl.specialist.searchexpert.services.aws.AmazonService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class SpecialistServiceImpl implements SpecialistService{

    private final SpecialistRepo specialistRepo;

    private final OpinionRepo opinionRepo;

    private final AmazonService amazonService;

    public SpecialistServiceImpl(SpecialistRepo specialistRepo, OpinionRepo opinionRepo, AmazonService amazonService) {
        this.specialistRepo = specialistRepo;
        this.opinionRepo = opinionRepo;
        this.amazonService = amazonService;
    }

    @Override
    public Specialist updateSpecialistAccount(Specialist specialist){
        Specialist existingSpecialist = specialistRepo.findBySpecialistId(specialist.getSpecialistId());
        if(existingSpecialist.getSpecialistId() == null) throw new SpecialistNotFoundException("Cannot Update Specialist with email: '" + specialist.getSpecialistId() + "' doesn't exist");
        else{
            if(!specialist.getMail().equals(existingSpecialist.getMail()))throw new SpecialistIdException("Cannot change mail");
            if(!specialist.getPhoneNumber().equals(existingSpecialist.getPhoneNumber()))throw new SpecialistIdException("Cannot change phone number");
            if(!specialist.getName().equals(existingSpecialist.getName()) || !specialist.getSurname().equals(existingSpecialist.getSurname()) )throw new SpecialistIdException("Cannot change personal dates");
        }
        return specialistRepo.save(specialist);
    }

    @Override
    public Specialist updateSpecialistRate(Specialist specialist, Integer stars){

        Specialist existingSpecialist = specialistRepo.findBySpecialistId(specialist.getSpecialistId());
        if(existingSpecialist == null) throw new SpecialistNotFoundException("Cannot rate specialist with ID: '" + specialist.getSpecialistId() + "' because doesn't exist");
//        else {
//            specialist.setNumberOfRatings(specialist.getNumberOfRatings()+1);
//            specialist.setRateStars((specialist.getRateStars()+stars)/(specialist.getNumberOfRatings()));
//        }
        return specialistRepo.save(specialist);
    }

    @Override
    public Specialist uploadProfileImg(MultipartFile file, String specialistId) throws IOException {



        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        specialist.setProfileImg(amazonService.uploadFile(file));
        return specialistRepo.save(specialist);
    }

    public void deleteSpecialistFromAllCustomersFavouriteLists(Set<Customer> setOfAllCustomerWhichMarkedConcreteSpecialist,String specialistId){
        for(Customer cust : setOfAllCustomerWhichMarkedConcreteSpecialist){
            cust.getMarkedSpecialists().remove(specialistRepo.findBySpecialistId(specialistId));
        }
        setOfAllCustomerWhichMarkedConcreteSpecialist.clear();
    }

    @Override
    public void deleteSpecialistBySpecialistId(String specialistId){
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        deleteSpecialistFromAllCustomersFavouriteLists(specialist.getMarkedByCustomers(),specialistId);
        specialistRepo.delete(specialist);
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
    public HashSet<Specialist> findSpecialistsByPersonalIdentity(String name, String surname){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialists isn't exist");
        HashSet<Specialist> groupOfSpecialists = specialistRepo.findSpecialistsByNameAndSurname(name,surname);
        if(groupOfSpecialists.size() == 0) throw new SpecialistNotFoundException("Any Specialist with the given data was not found");
        return groupOfSpecialists;
    }

    @Override
    public HashSet<Specialist> findSpecialistsByProfessionAndLocation(Province province, String city, String profession){
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
    @Override
    public Iterable<Specialist> findAllSpecialists(){
        if(specialistRepo.count() == 0) throw new SpecialistNotFoundException("Any Specialists isn't exist");
        return specialistRepo.findAll();
    }
}
