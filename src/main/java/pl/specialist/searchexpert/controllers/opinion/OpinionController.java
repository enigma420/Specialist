package pl.specialist.searchexpert.controllers.opinion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.request.CustomerIdSpecialistIdBody;
import pl.specialist.searchexpert.request.OpinionWithCustomerAndSpecialist;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.opinion.OpinionServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;

@RestController
@RequestMapping("/api/opinion")
@CrossOrigin
@Validated
public class OpinionController {

    private final MapValidationErrorService mapValidationErrorService;

    private final OpinionServiceImpl opinionServiceImpl;


    public OpinionController(OpinionServiceImpl opinionServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.opinionServiceImpl = opinionServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addOpinion(@Valid @RequestBody OpinionWithCustomerAndSpecialist opinionWithCustomerAndSpecialist){

        Opinion newOpinion = opinionServiceImpl.createOpinion(opinionWithCustomerAndSpecialist.getOpinion(),
                opinionWithCustomerAndSpecialist.getCustomerId(),
                opinionWithCustomerAndSpecialist.getSpecialistId());
        return new ResponseEntity<>(newOpinion, HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editOpinion(@Valid @RequestBody OpinionWithCustomerAndSpecialist opinionWithCustomerAndSpecialist){


        Opinion existingOpinion = opinionServiceImpl.updateOpinion(opinionWithCustomerAndSpecialist.getOpinion(),
                                                                    opinionWithCustomerAndSpecialist.getCustomerId(),
                                                                    opinionWithCustomerAndSpecialist.getSpecialistId());
        return new ResponseEntity<>(existingOpinion,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}/{specialistId}")
    public ResponseEntity<?> deleteOpinion(@PathVariable("customerId") String customerId,@PathVariable("specialistId") String specialistId){
        opinionServiceImpl.deleteOpinionByCustomerIdAndSpecialistId(customerId,specialistId);
        return new ResponseEntity<>("Opinion was deleted" , HttpStatus.OK);
    }

    @GetMapping("/specialist/getAll/{specialistId}")
    public ResponseEntity<?> getAllSpecialistOpinions(@PathVariable("specialistId") String specialistId){
        Iterable<Opinion> allSpecialistOpinions = opinionServiceImpl.findAllSpecialistOpinions(specialistId);
        return new ResponseEntity<>(allSpecialistOpinions,HttpStatus.OK);
    }


    @GetMapping("/customer/getAll/{customerId}")
    public ResponseEntity<?> getAllCustomerOpinions(@PathVariable("customerId") String customerId){
        HashSet<Opinion> allCustomerOpinions = opinionServiceImpl.findAllCustomerOpinions(customerId);
        return new ResponseEntity<>(allCustomerOpinions,HttpStatus.OK);
    }

    @GetMapping("/get/{specialistId}/{customerId}")
    public ResponseEntity<?> getConcreteCustomerOpinionToConcreteSpecialist(@PathVariable("customerId") String customerId,@PathVariable("specialistId") String specialistId){
        return new ResponseEntity<>(opinionServiceImpl.findConcreteCustomerOpinionToConcreteSpecialist(customerId,specialistId),HttpStatus.OK);
    }



}
