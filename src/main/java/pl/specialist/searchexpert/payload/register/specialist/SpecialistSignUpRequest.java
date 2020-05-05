package pl.specialist.searchexpert.payload.register.specialist;

import org.springframework.data.annotation.Transient;
import pl.specialist.searchexpert.domains.specialist.Province;

import javax.validation.constraints.*;

public class SpecialistSignUpRequest {
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 20, message = "Name '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String name;
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 20, message = "Name '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String surname;
    @NotNull(message = "Province may not be blank")
    private Province province;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String city;
    @NotBlank(message = "Profession may not be blank")
    @Size(min = 3, max = 25, message = "Profession '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String profession;
    @NotBlank(message = "Phone Number may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    private String mail;
    @Transient
    private String confirmMail;
    @NotBlank(message = "Password may not be blank")
    @Size(min = 5 , max = 30, message = "Password '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String password;
    @Transient
    private String confirmPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public String getConfirmMail() {
        return confirmMail;
    }

    public void setConfirmMail(String confirmMail) {
        this.confirmMail = confirmMail;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
