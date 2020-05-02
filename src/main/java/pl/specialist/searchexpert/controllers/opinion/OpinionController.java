package pl.specialist.searchexpert.controllers.opinion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.opinion.OpinionServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;

@RestController
@RequestMapping("/api/opinion")
@CrossOrigin
public class OpinionController {

    private final MapValidationErrorService mapValidationErrorService;

    private final OpinionServiceImpl opinionServiceImpl;


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

    @PostMapping("/edit/{specialistId}/{customerId}")
    public ResponseEntity<?> editOpinion(@Valid @RequestBody Opinion opinion, BindingResult bindingResult,@PathVariable("specialistId") String specialistId,@PathVariable("customerId") String customerId){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Opinion existingOpinion = opinionServiceImpl.updateOpinion(opinion,customerId,specialistId);
        return new ResponseEntity<>(existingOpinion,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{opinionId}")
    public ResponseEntity<?> deleteOpinion(@PathVariable("opinionId") String opinionId, String nickname){
        opinionServiceImpl.deleteOpinionByOpinionId(opinionId,nickname);
        return new ResponseEntity<>("Opinion with ID: '" + opinionId + "from Customer: '" + nickname + "' was deleted" , HttpStatus.OK);
    }

    @GetMapping("/getAll/{specialistId}")
    public ResponseEntity<?> getAllSpecialistOpinions(@PathVariable("specialistId") String specialistId){
        Iterable<Opinion> allSpecialistOpinions = opinionServiceImpl.findAllSpecialistOpinions(specialistId);
        return new ResponseEntity<>(allSpecialistOpinions,HttpStatus.OK);
    }


    @GetMapping("/getAll/{customerId}")
    public ResponseEntity<?> getAllCustomerOpinions(@PathVariable("customerId") String customerId){
        HashSet<Opinion> allCustomerOpinions = opinionServiceImpl.findAllCustomerOpinions(customerId);
        return new ResponseEntity<>(allCustomerOpinions,HttpStatus.OK);
    }

    @GetMapping("/get/{specialistId}/{customerId}")
    public ResponseEntity<?> getConcreteCustomerOpinionToConcreteSpecialist(@PathVariable("specialistId") String specialistId, @PathVariable("customerId") String customerId){
        return new ResponseEntity<>(opinionServiceImpl.findConcreteCustomerOpinionToConcreteSpecialist(customerId,specialistId),HttpStatus.OK);
    }



}
