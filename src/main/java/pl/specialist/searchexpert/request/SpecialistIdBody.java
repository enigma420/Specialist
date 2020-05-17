package pl.specialist.searchexpert.request;

public class SpecialistIdBody {

    private String specialistId;

    public SpecialistIdBody(String specialistId) {
        this.specialistId = specialistId;
    }

    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }
}
