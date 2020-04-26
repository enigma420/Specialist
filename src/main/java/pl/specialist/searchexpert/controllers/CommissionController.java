package pl.specialist.searchexpert.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.customer.Customer;
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

    public CommissionController(CommissionServiceImpl commissionServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.commissionServiceImpl = commissionServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCommission(@Valid @RequestBody Commission commission, Customer customer, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Commission newCommission = commissionServiceImpl.createCommission(commission,customer.getNickname());
        return new ResponseEntity<Commission>(newCommission,HttpStatus.CREATED);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editCommission(@Valid @RequestBody Commission commission, BindingResult bindingResult,String nickname){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Commission existingCommission = commissionServiceImpl.updateCommission(commission,nickname);
        return new ResponseEntity<>(existingCommission,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commissionId}")
    public ResponseEntity<?> deleteCommission(@PathVariable("commissionId") String commissionId, String nickname){
        commissionServiceImpl.deleteCommissionByCommissionId(commissionId,nickname);
        return new ResponseEntity<>("Commission with ID: '" + commissionId + "' was deleted" , HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllCommissions(){
        commissionServiceImpl.deleteAllCommissions();
        return new ResponseEntity<>("All Customers was deleted", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCommissions(String nickname){
        Iterable<Commission> allCommissions = commissionServiceImpl.findAllCommissions(nickname);
        return new ResponseEntity<>(allCommissions,HttpStatus.OK);
    }

    @GetMapping("/getById/{commissionId}")
    public ResponseEntity<?> getCommissionById(@PathVariable("commissionId") String commissionId, String nickname){
        Commission commission = commissionServiceImpl.findCommissionByCommissionId(commissionId,nickname);

        return new ResponseEntity<>(commission,HttpStatus.OK);
    }

    @GetMapping("/getByTitle/{title}")
    public ResponseEntity<?> getCommissionByTitle(@PathVariable("title") String title){

        Commission commission = commissionServiceImpl.findCommissionByTitle(title);

        return new ResponseEntity<>(commission,HttpStatus.OK);
    }
    @GetMapping("/getByCity/{city}")
    public ResponseEntity<?> getCommissionsByCity(@PathVariable("city") String city){

        HashSet<Commission> listOfCommissions = commissionServiceImpl.findCommissionsByCity(city);

        return new ResponseEntity<>(listOfCommissions,HttpStatus.OK);
    }

    @GetMapping("/getByProfession/{profession}")
    public ResponseEntity<?> getCommissionsByProfession(@PathVariable("profession") String profession){

        HashSet<Commission> listOfCommissions = commissionServiceImpl.findCommissionsByProfession(profession);

        return new ResponseEntity<>(listOfCommissions,HttpStatus.OK);
    }

    @GetMapping("/getByCustomerNickname/{nickname}")
    public ResponseEntity<?> getCommissionsByCustomerNickname(@PathVariable("nickname") String nickname){

        HashSet<Commission> listOfCustomerCommissions = commissionServiceImpl.findCommissionsByCommissionAuthorNickname(nickname);

        return new ResponseEntity<>(listOfCustomerCommissions,HttpStatus.OK);
    }

}
