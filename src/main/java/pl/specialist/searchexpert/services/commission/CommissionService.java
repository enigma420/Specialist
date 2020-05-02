package pl.specialist.searchexpert.services.commission;

import pl.specialist.searchexpert.domains.commission.Commission;

import java.util.HashSet;

public interface CommissionService {

    Commission createCommission(Commission commission, String nickname);
    Commission updateCommission(Commission commission, String nickname);
    void deleteCommissionByCommissionId(String commissionId,String nickname);
    Iterable<Commission> findAllCustomerCommissions(String nickname);
    Commission findCommissionByCommissionId(String commissionId,String username);
    HashSet<Commission> findCommissionsByCity(String city);
    HashSet<Commission> findCommissionsByProfession(String profession);


}
