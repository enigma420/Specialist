package pl.specialist.searchexpert.controllers.opinion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.request.OpinionWithSpecialistId;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.opinion.OpinionServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;

@RestController
@RequestMapping("/api/opinion")
@CrossOrigin
@Validated
public class OpinionController {


    private final OpinionServiceImpl opinionServiceImpl;


    public OpinionController(OpinionServiceImpl opinionServiceImpl) {
        this.opinionServiceImpl = opinionServiceImpl;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addOpinion(@Valid @RequestBody OpinionWithSpecialistId opinionWithSpecialistId, Principal principal){

        Opinion newOpinion = opinionServiceImpl.createOpinion(opinionWithSpecialistId.getOpinion(),
                principal.getName(),
                opinionWithSpecialistId.getSpecialistId());
        return new ResponseEntity<>(newOpinion, HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editOpinion(@Valid @RequestBody OpinionWithSpecialistId opinionWithSpecialistId, Principal principal){
        Opinion existingOpinion = opinionServiceImpl.updateOpinion(opinionWithSpecialistId.getOpinion(),
                                                                    principal.getName(),
                                                                    opinionWithSpecialistId.getSpecialistId());
        return new ResponseEntity<>(existingOpinion,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{specialistId}")
    public ResponseEntity<?> deleteOpinion(@PathVariable("specialistId") String specialistId,Principal principal){
        opinionServiceImpl.deleteOpinionByCustomerIdAndSpecialistId(principal.getName(),specialistId);
        return new ResponseEntity<>("Opinion was deleted" , HttpStatus.OK);
    }

    @GetMapping("/specialist/getAll")
    public ResponseEntity<?> getAllSpecialistOpinions(Principal principal){
        Iterable<Opinion> allSpecialistOpinions = opinionServiceImpl.findAllSpecialistOpinions(principal.getName());
        return new ResponseEntity<>(allSpecialistOpinions,HttpStatus.OK);
    }


    @GetMapping("/customer/getAll")
    public ResponseEntity<?> getAllCustomerOpinions(Principal principal){
        HashSet<Opinion> allCustomerOpinions = opinionServiceImpl.findAllCustomerOpinions(principal.getName());
        return new ResponseEntity<>(allCustomerOpinions,HttpStatus.OK);
    }

    @GetMapping("/get/{specialistId}")
    public ResponseEntity<?> getConcreteCustomerOpinionToConcreteSpecialist(@PathVariable("specialistId") String specialistId,Principal principal){
        return new ResponseEntity<>(opinionServiceImpl.findConcreteCustomerOpinionToConcreteSpecialist(principal.getName(),specialistId),HttpStatus.OK);
    }



}
