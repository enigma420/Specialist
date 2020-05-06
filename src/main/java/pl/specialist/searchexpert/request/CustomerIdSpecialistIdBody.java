package pl.specialist.searchexpert.request;

public class CustomerIdSpecialistIdBody {

    private String customerId;
    private String specialistId;

    public CustomerIdSpecialistIdBody() {
    }

    public CustomerIdSpecialistIdBody(String customerId, String specialistId) {
        this.customerId = customerId;
        this.specialistId = specialistId;
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
