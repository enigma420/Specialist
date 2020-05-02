package pl.specialist.searchexpert.controllers.specialist;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.specialist.SpecialistServiceImpl;

import javax.validation.Valid;
import java.util.HashSet;

@RestController
@RequestMapping("api/specialist")
@CrossOrigin
public class SpecialistController {

    private final SpecialistServiceImpl specialistServiceImpl;

    private final MapValidationErrorService mapValidationErrorService;

    public SpecialistController(SpecialistServiceImpl specialistServiceImpl, MapValidationErrorService mapValidationErrorService) {
        this.specialistServiceImpl = specialistServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSpecialistAccount(@Valid @RequestBody Specialist specialist, BindingResult bindingResult){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Specialist newSpecialist = specialistServiceImpl.createSpecialistAccount(specialist);
        return new ResponseEntity<>(newSpecialist, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSpecialistAccount(@Valid @RequestBody Specialist specialist, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Specialist existingSpecialist = specialistServiceImpl.updateSpecialistAccount(specialist);
        return new ResponseEntity<>(existingSpecialist,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{specialistId}")
    public ResponseEntity<?> deleteSpecialist(@PathVariable("specialistId") String specialistId){
        specialistServiceImpl.deleteSpecialistBySpecialistId(specialistId);
        return new ResponseEntity<>("Specialist with ID: '" + specialistId + "' was deleted",HttpStatus.OK);
    }

    @GetMapping("/getById/{specialistId}")
    public ResponseEntity<?> getSpecialistById(@PathVariable("specialistId") String specialistId){

        Specialist specialist = specialistServiceImpl.findSpecialistById(specialistId);

        return new ResponseEntity<>(specialist,HttpStatus.OK);
    }

    @GetMapping("/getByMail/{mail}")
    public ResponseEntity<?> getSpecialistByMail(@PathVariable("mail") String mail){

        Specialist specialist = specialistServiceImpl.findSpecialistByMail(mail);

        return new ResponseEntity<>(specialist,HttpStatus.OK);
    }

    @GetMapping("/getByPersonal")
    public ResponseEntity<HashSet<Specialist>> getSpecialistsByPersonalIdentity(@RequestParam(value = "name", required = false) String name,
                                                                 @RequestParam(value = "surname", required = false) String surname){
        HashSet<Specialist> groupOfSpecialists = specialistServiceImpl.findSpecialistsByPersonalIdentity(name,surname);
        return new ResponseEntity<>(groupOfSpecialists,HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<HashSet<Specialist>> getSpecialistsByProvinceAndCityAndProfession(@RequestParam(value = "province",required = false) Province province,
                                                                            @RequestParam(value = "city",required = false)String city,
                                                                            @RequestParam(value = "profession", required = false) String profession){
        HashSet<Specialist> groupOfSpecialists = specialistServiceImpl.findSpecialistsByProfessionAndLocation(province,city,profession);
        return new ResponseEntity<>(groupOfSpecialists,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Specialist>> getAllSpecialists(){
       Iterable<Specialist> allSpecialists = specialistServiceImpl.findAllSpecialists();
        return new ResponseEntity<>(allSpecialists,HttpStatus.OK);
    }

    @GetMapping("/get/opinions/{specialistId}")
    public ResponseEntity<HashSet<Opinion>> getAllSpecialistOpinions(@PathVariable("specialistId") String specialistId){
        HashSet<Opinion> allSpecialistOpinions = specialistServiceImpl.findAllSpecialistOpinionsById(specialistId);
        return new ResponseEntity<>(allSpecialistOpinions,HttpStatus.OK);
    }

}
