package pl.specialist.searchexpert.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.commission.CommissionServiceImpl;
import pl.specialist.searchexpert.services.opinion.OpinionServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;

@RestController
@RequestMapping("/api/opinion")
@CrossOrigin
public class OpinionController {

    private final MapValidationErrorService mapValidationErrorService;

    private final OpinionServiceImpl opinionServiceImpl;

    @Autowired
    public OpinionController(OpinionServiceImpl opinionServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.opinionServiceImpl = opinionServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }


    @PostMapping("/add/{specialistId}/{customerId}")
    public ResponseEntity<?> addOpinion(@Valid @RequestBody Opinion opinion, @PathVariable("customerId") String customerId,@PathVariable("specialistId") String specialistId, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Opinion newOpinion = opinionServiceImpl.createOpinion(opinion,customerId,specialistId);
        return new ResponseEntity<Opinion>(newOpinion, HttpStatus.CREATED);
    }

//    @PostMapping("/edit")
//    public ResponseEntity<?> editOpinion(@Valid @RequestBody Opinion opinion, BindingResult bindingResult,String nickname){
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
//        if(errorMap != null) return errorMap;
//
//        Opinion existingOpinion = opinionServiceImpl.updateOpinion(opinion,nickname);
//        return new ResponseEntity<>(existingOpinion,HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{opinionId}")
    public ResponseEntity<?> deleteOpinion(@PathVariable("opinionId") Long opinionId, String nickname){
        opinionServiceImpl.deleteOpinionByOpinionId(opinionId,nickname);
        return new ResponseEntity<>("Opinion with ID: '" + opinionId + "from Customer: '" + nickname + "' was deleted" , HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllOpinions(){
        opinionServiceImpl.deleteAllOpinions();
        return new ResponseEntity<>("All Opinions was deleted", HttpStatus.OK);
    }

    @GetMapping("/specialist/getAll")
    public ResponseEntity<?> getAllSpecialistOpinions(String specialistId){
        Iterable<Opinion> allOpinions = opinionServiceImpl.findAllSpecialistOpinions(specialistId);
        return new ResponseEntity<>(allOpinions,HttpStatus.OK);
    }

//    @GetMapping("/getById/{opinionId}")
//    public ResponseEntity<?> getOpinionById(@PathVariable("opinionId") String opinionId, String nickname){
//        Opinion opinion = opinionServiceImpl.findConcreteCustomerOpinionToConcreteSpecialist(commissionId,nickname);
//
//        return new ResponseEntity<>(commission,HttpStatus.OK);
//    }
//
//    @GetMapping("/getByCity/{city}")
//    public ResponseEntity<?> getCommissionsByCity(@PathVariable("city") String city){
//
//        HashSet<Commission> listOfCommissions = commissionServiceImpl.findCommissionsByCity(city);
//
//        return new ResponseEntity<>(listOfCommissions,HttpStatus.OK);
//    }
//
//    @GetMapping("/getByProfession/{profession}")
//    public ResponseEntity<?> getCommissionsByProfession(@PathVariable("profession") String profession){
//
//        HashSet<Commission> listOfCommissions = commissionServiceImpl.findCommissionsByProfession(profession);
//
//        return new ResponseEntity<>(listOfCommissions,HttpStatus.OK);
//    }
//
//    @GetMapping("/getByCustomerNickname/{nickname}")
//    public ResponseEntity<?> getCommissionsByCustomerNickname(@PathVariable("nickname") String nickname){
//
//        HashSet<Commission> listOfCustomerCommissions = commissionServiceImpl.findCommissionsByCommissionAuthorNickname(nickname);
//
//        return new ResponseEntity<>(listOfCustomerCommissions,HttpStatus.OK);
//    }

}
