package pl.specialist.searchexpert.services.opinion;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.CustomerAlreadyExistInFavouriteException;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionIdException;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionIdException;
import pl.specialist.searchexpert.exceptions.opinion.exceptions.OpinionNotFoundException;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;
import pl.specialist.searchexpert.repositories.opinion.OpinionRepo;

import javax.transaction.Transactional;
import java.lang.annotation.Documented;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        if(findConcreteCustomerOpinionToConcreteSpecialist(customerId,specialistId) != null) throw new OpinionIdException("Specialist already have got opinion from You !");
        try{
            Customer customer = customerRepo.findByCustomerId(customerId);
            opinion.setCustomer(customer);
            Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
            opinion.setSpecialist(specialist);
            specialist.setNumberOfRatings(specialist.getNumberOfRatings()+1);
            specialist.setSumOfRatingsValue(specialist.getSumOfRatingsValue()+opinion.getRate());
            specialist.setAverageRate(specialist.getSumOfRatingsValue()/specialist.getNumberOfRatings());
            return opinionRepo.save(opinion);
        }catch (Exception e){
            throw new OpinionIdException("Opinion with ID '" + opinion.getOpinionId() + "' already exist");
        }

    }

    @Override
    public Opinion updateOpinion(Opinion opinion, String customerId, String specialistId) {
        Opinion existingOpinion = opinionRepo.findByOpinionId(opinion.getOpinionId());
        if(existingOpinion.getOpinionId() == null) throw new OpinionNotFoundException("Cannot Update Opinion with ID: '" + opinion.getOpinionId() + "' doesn't exist");
        opinion.setCustomer(customerRepo.findByCustomerId(customerId));
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        opinion.setSpecialist(specialist);
        /*TODO Create logic for stars when customer edit opinion*/
//        HashMap<String,Double> rates = (HashMap<String, Double>) specialist.getRateStars();
//        rates.replace(opinion.getOpinionId(),opinion.getRate());
//        specialist.setAverageRate(sumAllRatesValuesFromHashMap(rates));
        return opinionRepo.save(opinion);
    }

    @Override
    public void deleteOpinionByCustomerIdAndSpecialistId(String customerId, String specialistId) {
        Specialist foundSpecialist = specialistRepo.findBySpecialistId(specialistId);
        Customer foundCustomer = customerRepo.findByCustomerId(customerId);
        if(findConcreteCustomerOpinionToConcreteSpecialist(customerId,specialistId) == null) throw new OpinionNotFoundException("You cannot delete Opinion because doesn't exist");
        opinionRepo.deleteByCustomerAndSpecialist(foundCustomer,foundSpecialist);
    }


    @Override
    public Iterable<Opinion> findAllSpecialistOpinions(String specialistId) {
        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        HashSet<Opinion> allOpinionsOfSpecialist = opinionRepo.findOpinionsBySpecialist(specialist);
        if(allOpinionsOfSpecialist.isEmpty()) throw new OpinionNotFoundException("Any Opinion isn't exist");
        return allOpinionsOfSpecialist;
    }

    @Override
    public Opinion findConcreteCustomerOpinionToConcreteSpecialist(String customerId, String specialistId) {
        Specialist foundSpecialist = specialistRepo.findBySpecialistId(specialistId);
        Customer foundCustomer = customerRepo.findByCustomerId(customerId);
        Opinion opinion = opinionRepo.findByCustomerAndSpecialist(foundCustomer,foundSpecialist);
        return opinion;
    }

    @Override
    public HashSet<Opinion> findAllCustomerOpinions(String customerId) {
        Customer customer = customerRepo.findByCustomerId(customerId);
        HashSet<Opinion> allOpinionsByOneCustomer = opinionRepo.findOpinionsByCustomer(customer);
        if(allOpinionsByOneCustomer.isEmpty()) throw new OpinionNotFoundException("Any Opinion isn't exist");
        return allOpinionsByOneCustomer;
    }
}