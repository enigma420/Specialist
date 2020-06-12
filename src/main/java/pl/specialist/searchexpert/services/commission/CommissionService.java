package pl.specialist.searchexpert.services.commission;

import pl.specialist.searchexpert.domains.commission.Commission;

import java.util.HashSet;

public interface CommissionService {

    Commission createCommission(Commission commission, String customerMail);
//    Commission updateCommission(Commission commission, String nickname);
    void deleteCommissionByCommissionId(String commissionId,String nickname);
    Iterable<Commission> findAllCustomerCommissions(String nickname);
    Commission findCommissionByCommissionId(String commissionId,String username);
    HashSet<Commission> findCommissionsByProfessionAndLocation(String profession, String city);

}
