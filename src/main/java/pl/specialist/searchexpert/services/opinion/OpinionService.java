package pl.specialist.searchexpert.services.opinion;

import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.opinion.Opinion;

import java.util.HashSet;

public interface OpinionService {

    Opinion createOpinion(Opinion opinion, String customerNickname, String specialistId);
    Opinion updateOpinion(Opinion opinion, String customerNickname, String specialistId);
    void deleteOpinionByOpinionId(Long opinionId,String customerNickname);
    void deleteAllSpecialistOpinions(String specialistId);
    void deleteAllOpinions();
    Iterable<Opinion> findAllSpecialistOpinions(String specialistId);
    Iterable<Opinion> findAllOpinions();
    Opinion findConcreteCustomerOpinionToConcreteSpecialist(String customerId, String specialistId);
    HashSet<Opinion> findAllOpinionsByOneCustomer(String customerNickname);

}
