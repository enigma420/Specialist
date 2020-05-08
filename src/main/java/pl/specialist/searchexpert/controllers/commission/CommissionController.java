package pl.specialist.searchexpert.controllers.commission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.request.CommissionWithCustomerId;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.commission.CommissionServiceImpl;

import javax.validation.Valid;
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
    public ResponseEntity<?> addCommission(@Valid @RequestBody CommissionWithCustomerId commissionWithCustomerId){


        Commission newCommission = commissionServiceImpl.createCommission(commissionWithCustomerId.getCommission(),commissionWithCustomerId.getCustomerId());
        return new ResponseEntity<>(newCommission,HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editCommission(@Valid @RequestBody CommissionWithCustomerId commissionWithCustomerId){


        Commission existingCommission = commissionServiceImpl.updateCommission(commissionWithCustomerId.getCommission(),commissionWithCustomerId.getCustomerId());
        return new ResponseEntity<>(existingCommission,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commissionId}")
    public ResponseEntity<?> deleteCommission(@PathVariable("commissionId") String commissionId, String nickname){
        commissionServiceImpl.deleteCommissionByCommissionId(commissionId,nickname);
        return new ResponseEntity<>("Commission with ID: '" + commissionId + "' was deleted" , HttpStatus.OK);
    }

    @GetMapping("/getAll/{customerId}")
    public ResponseEntity<?> getCustomerAllCommissions(@PathVariable("customerId") String customerId){
        Iterable<Commission> allCommissions = commissionServiceImpl.findAllCustomerCommissions(customerId);
        return new ResponseEntity<>(allCommissions,HttpStatus.OK);
    }

    @GetMapping("/getById/{commissionId}")
    public ResponseEntity<?> getCommissionById(@PathVariable("commissionId") String commissionId, String nickname){
        Commission commission = commissionServiceImpl.findCommissionByCommissionId(commissionId,nickname);

        return new ResponseEntity<>(commission,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<HashSet<Commission>> getCommissionsByProfessionAndCity(@RequestParam(value = "profession",required = false) String profession,
                                                                                 @RequestParam(value = "city", required = false) String city){
        HashSet<Commission> setOfCommissions = commissionServiceImpl.findCommissionsByProfessionAndLocation(profession,city);
        return new ResponseEntity<>(setOfCommissions,HttpStatus.OK);
    }

}
