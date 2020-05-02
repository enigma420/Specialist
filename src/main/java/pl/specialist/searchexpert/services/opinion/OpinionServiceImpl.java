package pl.specialist.searchexpert.services.opinion;

import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Specialist;
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

    public Double sumAllRatesValuesFromHashMap(HashMap<String,Double> rates){
        Double avgRate = 0.0;
        for(Double rate : rates.values()){
            avgRate += rate;
        }
        return (avgRate/rates.size());
    }

    @Override
    public Opinion createOpinion(Opinion opinion, String customerId, String specialistId) {
        if(opinion.getOpinionId() != null) throw new OpinionIdException("Opinion Id is exists");
        try{

            Customer customer = customerRepo.findByCustomerId(customerId);
            opinion.setCustomer(customer);
            Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
            opinion.setSpecialist(specialist);
//            HashMap<String,Double> rates = specialist.getRateStars();
//            rates.put(opinion.getOpinionId(),opinion.getRate());
//            specialist.setAverageRate(sumAllRatesValuesFromHashMap(rates));
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
        HashMap<String,Double> rates = specialist.getRateStars();
        rates.replace(opinion.getOpinionId(),opinion.getRate());
        specialist.setAverageRate(sumAllRatesValuesFromHashMap(rates));
        return opinionRepo.save(opinion);
    }

    @Override
    public void deleteOpinionByOpinionId(String opinionId, String customerNickname) {
        if(opinionRepo.count() == 0) throw new OpinionNotFoundException("You cannot delete Opinion because doesn't exist");
        Customer customer = customerRepo.findByNickname(customerNickname);
        Opinion opinion = opinionRepo.findByOpinionIdAndCustomer(opinionId,customer);
        if(opinion == null) throw new OpinionIdException("Cannot Delete Opinion with ID: '" + opinionId + "' because doesn't exist");
    }


    @Override
    public Iterable<Opinion> findAllSpecialistOpinions(String specialistId) {
        if(opinionRepo.count() == 0) throw new OpinionNotFoundException("Any opinion isn't exist");

        Specialist specialist = specialistRepo.findBySpecialistId(specialistId);
        HashSet<Opinion> allOpinionsOfSpecialist = opinionRepo.findOpinionsBySpecialist(specialist);
        if(allOpinionsOfSpecialist.isEmpty()) throw new OpinionNotFoundException("Specialist with mail: '" + specialist.getMail() + "' doesn't have any opinions");
        return allOpinionsOfSpecialist;
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
    public HashSet<Opinion> findAllCustomerOpinions(String customerId) {
        Customer customer = customerRepo.findByCustomerId(customerId);
        HashSet<Opinion> allOpinionsByOneCustomer = opinionRepo.findOpinionsByCustomer(customer);
        if(allOpinionsByOneCustomer.isEmpty()) throw new OpinionNotFoundException("Customer with ID: '" + customer.getCustomerId() + "' doesn't have any opinions");
        return allOpinionsByOneCustomer;
    }
}
