package pl.specialist.searchexpert.request;

import pl.specialist.searchexpert.domains.opinion.Opinion;

public class OpinionWithCustomerAndSpecialist {

    private Opinion opinion;
    private String customerId;
    private String specialistId;

    public OpinionWithCustomerAndSpecialist(Opinion opinion, String customerId, String specialistId) {
        this.opinion = opinion;
        this.customerId = customerId;
        this.specialistId = specialistId;
    }

    public OpinionWithCustomerAndSpecialist() {
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }
}
