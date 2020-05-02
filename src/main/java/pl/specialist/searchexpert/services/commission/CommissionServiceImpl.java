package pl.specialist.searchexpert.services.commission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionIdException;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionNotFoundException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.commission.CommissionRepo;

import javax.transaction.Transactional;
import java.util.HashSet;

@Service
@Transactional
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepo commissionRepo;

    private final CustomerRepo customerRepo;

    @Autowired
    public CommissionServiceImpl(CommissionRepo commissionRepo, CustomerRepo customerRepo) {
        this.commissionRepo = commissionRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Commission createCommission(Commission commission,String customerId) {
        try{
        Customer customer = customerRepo.findByCustomerId(customerId);
            commission.setCustomer(customer);

        return commissionRepo.save(commission);
        }catch (Exception e){
            throw new CommissionIdException("Commission with ID '" + commission.getCommissionId() + "' already exist" +
            "' ,city: '" + commission.getCity() +
            "' ,customer: '" + commission.getCustomer() +
            "' ,title: '" + commission.getTitle() +
            "' ,description: '"+ commission.getDescription() +
            "' ,profession: '" + commission.getProfession() +"'");
        }

    }

    @Override
    public Commission updateCommission(Commission commission,String customerId) {
        Customer customer = customerRepo.findByCustomerId(customerId);
        if(customer.getCommissions().isEmpty()) throw new CommissionNotFoundException("Customer with ID: '" + customerId + "' don't have any commission");
        Commission existingCommission = commissionRepo.findByCommissionId(commission.getCommissionId());
        if(existingCommission.getCommissionId() == null) throw new CommissionNotFoundException("Cannot Update Commission with ID: '" + commission.getCommissionId() + "' doesn't exist");
        commission.setCustomer(customerRepo.findByCustomerId(customerId));
        return commissionRepo.save(commission);
    }

    @Override
    public void deleteCommissionByCommissionId(String commissionId,String nickname) {
        if(commissionRepo.count() == 0) throw new CommissionNotFoundException("You cannot delete Commission because doesn't exist");
        Commission commission = commissionRepo.findByCommissionId(commissionId);
        if(commission == null) throw new CommissionIdException("Cannot Delete Commission with ID: '" + commissionId + "' because doesn't exist");
        commissionRepo.delete(findCommissionByCommissionId(commissionId,nickname));
    }



    @Override
    public Commission findCommissionByCommissionId(String commissionId,String nickname) {
        Commission commission = commissionRepo.findByCommissionId(commissionId);
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        if(commission == null) throw new CommissionIdException("Commission with ID: '" + commissionId + "' doesn't exist");
        if(!commission.getCustomer().getNickname().equals(nickname)) throw new CommissionNotFoundException("Commission not found in your account");

        return commission;
    }


    @Override
    public HashSet<Commission> findCommissionsByProfession(String profession) {
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        HashSet<Commission> setOfCommissions = commissionRepo.findByProfession(profession);
        if(setOfCommissions.size() == 0) throw new CommissionNotFoundException("Any Commissions with profession: '" + profession + "' doesn't exist");
        return setOfCommissions;
    }

    @Override
    public HashSet<Commission> findCommissionsByCity(String city) {
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        HashSet<Commission> setOfCommissions = commissionRepo.findByCity(city);
        if(setOfCommissions.size() == 0) throw new CommissionNotFoundException("Any Commissions with profession: '" + city + "' doesn't exist");
        return setOfCommissions;
    }

    @Override
    public Iterable<Commission> findAllCustomerCommissions(String customerId) {
        Customer customer = customerRepo.findByCustomerId(customerId);
        if(customer.getCommissions().isEmpty()) throw new SpecialistNotFoundException("Customer with ID: '" + customerId + "' don't have any Commissions");

        return commissionRepo.findByCustomer(customer);
    }
}
