package pl.specialist.searchexpert.services.opinion;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionIdException;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionIdException;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionNotFoundException;
import pl.specialist.searchexpert.repositories.CustomerRepo;
import pl.specialist.searchexpert.repositories.SpecialistRepo;
import pl.specialist.searchexpert.repositories.opinion.OpinionRepo;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
@Transactional
public class OpinionServiceImpl implements OpinionService{


    private final OpinionRepo opinionRepo;

    private final CustomerRepo customerRepo;

    private final SpecialistRepo specialistRepo;

    public OpinionServiceImpl(OpinionRepo opinionRepo, CustomerRepo customerRepo, SpecialistRepo specialistRepo) {
        this.opinionRepo = opinionRepo;
        this.customerRepo = customerRepo;
        this.specialistRepo = specialistRepo;
    }

    @Override
    public Opinion createOpinion(Opinion opinion, String customerId, String specialistId) {
        try{
            Customer customer = customerRepo.findByCustomerId(customerId);
            opinion.setCustomer(customer);
            Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
            opinion.setSpecialist(specialist);
            specialist.setNumberOfRatings(specialist.getNumberOfRatings()+1);
            specialist.setRateStars((specialist.getRateStars()+opinion.getRate())/specialist.getNumberOfRatings());
            return opinionRepo.save(opinion);
        }catch (Exception e){
            throw new CommissionIdException("Opinion with ID '" + opinion.getOpinionId() + "' already exist");
        }
    }

    @Override
    public Opinion updateOpinion(Opinion opinion, String customerNickname, String specialistId) {
        return null;
    }

    @Override
    public void deleteOpinionByOpinionId(Long opinionId, String customerNickname) {
        if(opinionRepo.count() == 0) throw new OpinionNotFoundException("You cannot delete Opinion because doesn't exist");
        Customer customer = customerRepo.findByNickname(customerNickname);
        Opinion opinion = opinionRepo.findByOpinionIdAndCustomer(opinionId,customer);
        if(opinion == null) throw new OpinionIdException("Cannot Delete Opinion with ID: '" + opinionId + "' because doesn't exist");
    }

    @Override
    public void deleteAllSpecialistOpinions(String specialistId) {
        if(opinionRepo.count() == 0) throw new OpinionNotFoundException("You cannot delete Opinion because doesn't exist");

        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
//        HashSet<Opinion> allOpinionOfSpecialist = opinionRepo.findOpinionsBySpecialist(specialist);
        opinionRepo.deleteAllBySpecialist(specialist);
    }

    @Override
    public void deleteAllOpinions() {
        opinionRepo.deleteAll();
    }

    @Override
    public Iterable<Opinion> findAllSpecialistOpinions(String specialistId) {
        if(opinionRepo.count() == 0) throw new OpinionNotFoundException("You cannot delete Opinion because doesn't exist");

        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        HashSet<Opinion> allOpinionOfSpecialist = opinionRepo.findOpinionsBySpecialist(specialist);
        return allOpinionOfSpecialist;
    }

    @Override
    public Iterable<Opinion> findAllOpinions() {
        return opinionRepo.findAll();
    }

    @Override
    public Opinion findConcreteCustomerOpinionToConcreteSpecialist(String customerId, String specialistId) {
        if(opinionRepo.count() == 0) throw new OpinionNotFoundException("You cannot delete Opinion because doesn't exist");
        Specialist foundSpecialist = specialistRepo.findBySpecialistId(specialistId);
        Customer foundCustomer = customerRepo.findByCustomerId(customerId);
        Opinion opinion = opinionRepo.findByCustomerAndSpecialist(foundCustomer,foundSpecialist);
        return opinion;
    }

    @Override
    public HashSet<Opinion> findAllOpinionsByOneCustomer(String customerNickname) {
        Customer customer = customerRepo.findByNickname(customerNickname);
        HashSet<Opinion> allOpinionsByOneCustomer = opinionRepo.findOpinionsByCustomer(customer);
        return allOpinionsByOneCustomer;
    }
}
