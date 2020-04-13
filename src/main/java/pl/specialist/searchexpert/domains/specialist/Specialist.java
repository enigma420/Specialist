package pl.specialist.searchexpert.domains.specialist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Specialist {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String specialistId;
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 32 characters")
    String name;
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 32 characters")
    String surname;
//    @NotBlank(message = "Province may not be blank")
    Province province;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City must be between 2 and 32 characters")
    String city;
//    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @NotBlank(message = "Profession may not be blank")
    @Size(min = 3, max = 25, message = "Profession must be between 2 and 32 characters")
//    @ElementCollection
//    @CollectionTable(name = "listOfProfession")
    String profession;
    @NotBlank(message = "Profession may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    String mail;

    public Specialist() {
    }

    public Specialist(String name,String surname,Province province, String city, String profession, String phoneNumber,String mail) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.city = city;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }

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
}
