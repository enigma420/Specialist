package pl.specialist.searchexpert.request;

import pl.specialist.searchexpert.domains.commission.Commission;

public class CommissionWithCustomerId {

    private Commission commission;
    private String customerId;

    public CommissionWithCustomerId() {
    }

    public CommissionWithCustomerId(Commission commission, String customerId) {
        this.commission = commission;
        this.customerId = customerId;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
