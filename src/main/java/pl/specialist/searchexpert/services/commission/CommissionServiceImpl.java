package pl.specialist.searchexpert.services.commission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionIdException;
import pl.specialist.searchexpert.exceptions.commission.exceptions.CommissionNotFoundException;
import pl.specialist.searchexpert.exceptions.customer.exceptions.CustomerNotFoundException;
import pl.specialist.searchexpert.exceptions.specialist.exceptions.SpecialistNotFoundException;
import pl.specialist.searchexpert.repositories.CustomerRepo;
import pl.specialist.searchexpert.repositories.commission.CommissionRepo;

import java.util.HashSet;

@Service
public class CommissionServiceImpl implements CommissionService {

    private final CommissionRepo commissionRepo;

    private final CustomerRepo customerRepo;

    @Autowired
    public CommissionServiceImpl(CommissionRepo commissionRepo, CustomerRepo customerRepo) {
        this.commissionRepo = commissionRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public Commission createCommission(Commission commission,String nickname) {
//        if(commission.getId() != null){
//            Commission existingCommission = commissionRepo.findByCommissionId(commission.getCommissionId());
//            if(existingCommission != null && (!existingCommission.getCommissionAuthorNickname().equals(nickname))){
//                throw new CommissionNotFoundException("Commission not found in your account");
//            }else if(existingCommission == null){
//                throw new CommissionNotFoundException("Commission with ID: '" + commission.getCommissionId() + "' cannot be updated");
//            }
//        }
        try{
        Customer customer = customerRepo.findByNickname(nickname);
            commission.setCustomer(customer);
            commission.setCommissionAuthorNickname(customer.getNickname());

        return commissionRepo.save(commission);
        }catch (Exception e){
            throw new CommissionIdException("Commission with ID '" + commission.getCommissionId() + "' already exist" +
            "' ,author: '" + commission.getCommissionAuthorNickname() +
            "' ,city: '" + commission.getCity() +
            "' ,customer: '" + commission.getCustomer() +
            "' ,title: '" + commission.getTitle() +
            "' ,description: '"+ commission.getDescription() +
            "' ,profession: '" + commission.getProfession() +"'");
        }

    }

    @Override
    public Commission updateCommission(Commission commission,String nickname) {
        Commission existingCommission = commissionRepo.findByCommissionId(commission.getCommissionId());
        if(existingCommission.getCommissionId() == null) throw new CommissionNotFoundException("Cannot Update Commission with ID: '" + commission.getCommissionId() + "' doesn't exist");
        else{

        }

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
    public void deleteAllCommissions() {
        if(commissionRepo.count() == 0) throw new CommissionNotFoundException("You cannot delete Commission because doesn't exist");
        commissionRepo.deleteAll();
    }

    @Override
    public Iterable<Commission> findAllCommissions(String nickname) {
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        return commissionRepo.findAll();
    }

    @Override
    public Commission findCommissionByCommissionId(String commissionId,String nickname) {
        Commission commission = commissionRepo.findByCommissionId(commissionId);
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        if(commission == null) throw new CommissionIdException("Commission with ID: '" + commissionId + "' doesn't exist");
        if(!commission.getCommissionAuthorNickname().equals(nickname)) throw new CommissionNotFoundException("Commission not found in your account");

        return commission;
    }


    @Override
    public HashSet<Commission> findCommissionsByProfession(String profession) {
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        HashSet<Commission> setOfCommissions = commissionRepo.findCommissionsByProfession(profession);
        if(setOfCommissions.size() == 0) throw new CommissionNotFoundException("Any Commissions with profession: '" + profession + "' doesn't exist");
        return setOfCommissions;
    }

    @Override
    public HashSet<Commission> findCommissionsByCity(String city) {
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        HashSet<Commission> setOfCommissions = commissionRepo.findCommissionsByCity(city);
        if(setOfCommissions.size() == 0) throw new CommissionNotFoundException("Any Commissions with profession: '" + city + "' doesn't exist");
        return setOfCommissions;
    }

    @Override
    public HashSet<Commission> findCommissionsByCommissionAuthorNickname(String nickname) {
        if(commissionRepo.count() == 0) throw new SpecialistNotFoundException("Any Commission isn't exist");
        Customer soughtCustomer = customerRepo.findByNickname(nickname);
        if(soughtCustomer == null) throw new CustomerNotFoundException("Customer with nickname: '" + nickname + "' doesn't exist");
        HashSet<Commission> setOfCommissions = commissionRepo.findCommissionsByCommissionAuthorNickname(nickname);
        if(setOfCommissions.size() == 0) throw new CommissionNotFoundException("Any Commissions which create by Customer with nickname: '" + nickname + "' doesn't exist");
        return setOfCommissions;
    }
}
