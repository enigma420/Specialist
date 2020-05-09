package pl.specialist.searchexpert.services.opinion;

import pl.specialist.searchexpert.domains.opinion.Opinion;

import java.util.HashSet;

public interface OpinionService {

    Opinion createOpinion(Opinion opinion, String customerId, String specialistId);
    Opinion updateOpinion(Opinion opinion, String customerId, String specialistId);
    void deleteOpinionByCustomerIdAndSpecialistId(String customerId, String specialistId);
    Iterable<Opinion> findAllSpecialistOpinions(String specialistId);
    Opinion findConcreteCustomerOpinionToConcreteSpecialist(String customerId, String specialistId);
    HashSet<Opinion> findAllCustomerOpinions(String customerNickname);

}
