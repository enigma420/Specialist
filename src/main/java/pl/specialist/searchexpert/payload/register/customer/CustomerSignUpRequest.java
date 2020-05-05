package pl.specialist.searchexpert.payload.register.customer;

import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class CustomerSignUpRequest {

    @NotBlank(message = "Nickname may not be blank")
    @Size(min = 3, max = 20, message = "Nickname '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String nickname;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    private String city;
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


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
