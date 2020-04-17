package pl.specialist.searchexpert.domains.specialist;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import pl.specialist.searchexpert.domains.customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

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
    @NotNull(message = "Province may not be blank")
    Province province;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City must be between 2 and 32 characters")
    String city;
    //    @Size(min = 3, max = 25, message = "Profession must be between 2 and 32 characters")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @NotEmpty(message = "City may not be empty")
    @ElementCollection
    @Column(name = "professions", nullable = false)
    List<String> professions;
    @NotBlank(message = "Profession may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    String mail;
    @Min(0)
    @Max(5)
    Double stars = 0.0;

    @ManyToMany(mappedBy = "markedSpecialists")
    Set<Customer> marks;

    public Specialist() {
    }

    public Specialist(String name,String surname,Province province, String city, List<String> professions, String phoneNumber,String mail,Double stars) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.city = city;
        this.professions = professions;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.stars = stars;
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

    public List<String> getProfessions() {
        return professions;
    }

    public void setProfessions(List<String> professions) {
        this.professions = professions;
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

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }
}
