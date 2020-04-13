package pl.specialist.searchexpert.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.SpecialistService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/specialist")
@CrossOrigin
public class SpecialistController {

    private final SpecialistService specialistService;


    private final MapValidationErrorService mapValidationErrorService;

    public SpecialistController(SpecialistService specialistService, MapValidationErrorService mapValidationErrorService) {
        this.specialistService = specialistService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSpecialistAccount(@Valid @RequestBody Specialist specialist, BindingResult bindingResult){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Specialist newSpecialist = specialistService.createSpecialistAccount(specialist);
        return new ResponseEntity<>(newSpecialist, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSpecialistAccount(@Valid @RequestBody Specialist specialist, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Specialist existingSpecialist = specialistService.updateSpecialistAccount(specialist);
        return new ResponseEntity<>(existingSpecialist,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{specialistId}")
    public ResponseEntity<?> deleteSpecialist(@PathVariable("specialistId") String specialistId){
        specialistService.deleteSpecialistBySpecialistId(specialistId);
        return new ResponseEntity<>("Specialist with ID: '" + specialistId + "' was deleted",HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllSpecialists(){
        specialistService.deleteAllSpecialists();
        return new ResponseEntity<>("All Specialists was deleted",HttpStatus.OK);
    }

    @GetMapping("/getById/{specialistId}")
    public ResponseEntity<?> getSpecialistById(@PathVariable("specialistId") String specialistId){

        Specialist specialist = specialistService.findSpecialistById(specialistId);

        return new ResponseEntity<>(specialist,HttpStatus.OK);
    }

    @GetMapping("/getByMail/{mail}")
    public ResponseEntity<?> getSpecialistByMail(@PathVariable("mail") String mail){

        Specialist specialist = specialistService.findSpecialistByMail(mail);

        return new ResponseEntity<>(specialist,HttpStatus.OK);
    }

    @GetMapping("/getByPersonal/")
    public Iterable<Specialist> getSpecialistsByPersonalIdentity(@RequestParam(value = "name", required = false) String name,
                                                                 @RequestParam(value = "surname", required = false) String surname){
        return specialistService.findSpecialistsByPersonalIdentity(name,surname);
    }

    @GetMapping("/get/")
    public Iterable<Specialist> getSpecialistsByProvinceAndCityAndProfession(@RequestParam(value = "province",required = false) Province province,
                                                                             @RequestParam(value = "city",required = false)String city,
                                                                             @RequestParam(value = "profession", required = false) String profession){
        return specialistService.findSpecialists(province,city,profession);
    }

    @GetMapping("/getAll")
    public Iterable<Specialist> getAllSpecialists(){
        return specialistService.findAllSpecialists();
    }

}
