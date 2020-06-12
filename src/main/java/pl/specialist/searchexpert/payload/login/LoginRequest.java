package pl.specialist.searchexpert.payload.login;


import org.springframework.beans.factory.annotation.Autowired;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @Autowired
    private SpecialistRepo specialistRepo;

    @Autowired
    private CustomerRepo customerRepo;




    @NotBlank(message = "Mail cannot be blank")
    private String mail;
    @NotBlank(message = "Password cannot be blank")
    private String password;


    public String getMail() {

        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getIdByMail(){

            return customerRepo.findByMail(mail).getCustomerId();

    }

}