package pl.specialist.searchexpert.request;

import pl.specialist.searchexpert.domains.commission.Commission;

import java.security.Principal;

public class CommissionWithCustomerId {

    private Commission commission;
    private Principal principal;

    public CommissionWithCustomerId() {
    }

//    public CommissionWithCustomerId(Commission commission, String customerId) {
//        this.commission = commission;
//        this.customerId = customerId;
//    }

    public CommissionWithCustomerId(Commission commission, Principal principal) {
        this.commission = commission;
        this.principal = principal;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }


    //    public String getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(String customerId) {
//        this.customerId = customerId;
//    }
}
