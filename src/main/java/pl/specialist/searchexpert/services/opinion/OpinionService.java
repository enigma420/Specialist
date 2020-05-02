package pl.specialist.searchexpert.services.opinion;

import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.opinion.Opinion;

import java.util.HashSet;

public interface OpinionService {

    Opinion createOpinion(Opinion opinion, String customerId, String specialistId);
    Opinion updateOpinion(Opinion opinion, String customerId, String specialistId);
    void deleteOpinionByOpinionId(String opinionId,String customerNickname);
    Iterable<Opinion> findAllSpecialistOpinions(String specialistId);
    Opinion findConcreteCustomerOpinionToConcreteSpecialist(String customerId, String specialistId);
    HashSet<Opinion> findAllCustomerOpinions(String customerNickname);

}
