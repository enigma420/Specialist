package pl.specialist.searchexpert.request;

import pl.specialist.searchexpert.domains.opinion.Opinion;

public class OpinionWithSpecialistId {

    private Opinion opinion;
    private String specialistId;

    public OpinionWithSpecialistId(Opinion opinion, String specialistId) {
        this.opinion = opinion;
        this.specialistId = specialistId;
    }

    public OpinionWithSpecialistId() {
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }
}
