package pl.specialist.searchexpert.controllers.specialist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.specialist.searchexpert.domains.opinion.Opinion;
import pl.specialist.searchexpert.domains.specialist.Province;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.request.SpecialistIdBody;
import pl.specialist.searchexpert.services.MapValidationErrorService;
import pl.specialist.searchexpert.services.aws.AmazonService;
import pl.specialist.searchexpert.services.specialist.SpecialistServiceImpl;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;

@RestController
@RequestMapping("api/specialist")
@CrossOrigin
public class SpecialistController {

    private final AmazonService amazonService;

    private final SpecialistServiceImpl specialistServiceImpl;

    private final MapValidationErrorService mapValidationErrorService;


    public SpecialistController(SpecialistServiceImpl specialistServiceImpl, MapValidationErrorService mapValidationErrorService,AmazonService amazonService) {
        this.specialistServiceImpl = specialistServiceImpl;
        this.mapValidationErrorService = mapValidationErrorService;
        this.amazonService = amazonService;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateSpecialistAccount(@Valid @RequestBody Specialist specialist, BindingResult bindingResult){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if(errorMap != null) return errorMap;

        Specialist existingSpecialist = specialistServiceImpl.updateSpecialistAccount(specialist);
        return new ResponseEntity<>(existingSpecialist,HttpStatus.OK);
    }

    @PostMapping("/upload/profile_img")
    public ResponseEntity<?> uploadProfileImage(@RequestPart("image")MultipartFile imageFile, Principal principal)throws IOException{

        Specialist specialist = specialistServiceImpl.uploadProfileImg(imageFile,principal.getName());

//        return new ResponseEntity<>(specialist,HttpStatus.OK);
        return new ResponseEntity<>(specialist,HttpStatus.OK);

    }

    @DeleteMapping("/delete/profile_img")
    public String deleteProfileImage(@RequestPart("url") String fileUrl){

        return this.amazonService.deleteFileFromS3Bucket(fileUrl);
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
//
//    @GetMapping("/get/opinions")
//    public ResponseEntity<HashSet<Opinion>> getAllSpecialistOpinions(Principal principal){
//        HashSet<Opinion> allSpecialistOpinions = specialistServiceImpl.findAllSpecialistOpinionsById(principal.getName());
//        return new ResponseEntity<>(allSpecialistOpinions,HttpStatus.OK);
//    }

}
