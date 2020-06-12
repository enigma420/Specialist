package pl.specialist.searchexpert.controllers.commission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.commission.CommissionServiceImpl;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;

@RestController
@RequestMapping("/api/commission")
@CrossOrigin
public class CommissionController {


    private final CommissionServiceImpl commissionServiceImpl;

    private final MapValidationErrorService mapValidationErrorService;

    @Autowired
    public CommissionController(CommissionServiceImpl commissionServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.commissionServiceImpl = commissionServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCommission(@Valid @RequestBody Commission commission, BindingResult result,Principal principal){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Commission newCommission = commissionServiceImpl.createCommission(commission,principal.getName());
        return new ResponseEntity<>(newCommission,HttpStatus.CREATED);
    }

//
//    @PostMapping("/edit")
//    public ResponseEntity<?> editCommission(@Valid @RequestBody Commission commission,BindingResult result, Principal principal){
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//        if(errorMap != null) return errorMap;
//
//        Commission existingCommission = commissionServiceImpl.updateCommission(commission,principal.getName());
//        return new ResponseEntity<>(existingCommission,HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{commissionId}")
    public ResponseEntity<?> deleteCommission(@PathVariable("commissionId") String commissionId, Principal principal){
        commissionServiceImpl.deleteCommissionByCommissionId(commissionId,principal.getName());
        return new ResponseEntity<>("Commission with ID: '" + commissionId + "' was deleted" , HttpStatus.OK);
    }
//
    @GetMapping("/getAll")
    public ResponseEntity<?> getCustomerAllCommissions(Principal principal){
        Iterable<Commission> allCommissions = commissionServiceImpl.findAllCustomerCommissions(principal.getName());
        return new ResponseEntity<>(allCommissions,HttpStatus.OK);
    }

    @GetMapping("/get/{commissionId}")
    public ResponseEntity<?> getCommissionById(@PathVariable("commissionId") String commissionId, Principal principal){
        Commission commission = commissionServiceImpl.findCommissionByCommissionId(commissionId,principal.getName());
        return new ResponseEntity<>(commission,HttpStatus.OK);
    }

    @GetMapping("/getCommissions")
    public ResponseEntity<HashSet<Commission>> getCommissionsByProfessionAndCity(@RequestParam(value = "profession",required = false) String profession,
                                                                                 @RequestParam(value = "city", required = false) String city){
        HashSet<Commission> setOfCommissions = commissionServiceImpl.findCommissionsByProfessionAndLocation(profession,city);
        return new ResponseEntity<>(setOfCommissions,HttpStatus.OK);
    }

}
