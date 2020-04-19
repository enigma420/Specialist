package pl.specialist.searchexpert.domains.specialist;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import pl.specialist.searchexpert.domains.customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "specialist")
public class Specialist {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String specialistId;
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters")
    String name;
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters")
    String surname;
    @NotNull(message = "Province may not be blank")
    Province province;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City must be between 2 and 25 characters")
    String city;
    @NotBlank(message = "profession may not be blank")
    @Size(min = 3, max = 25, message = "Profession must be between 2 and 25 characters")
    String profession;
    @NotBlank(message = "Profession may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    String mail;
    @Min(0)
    @Max(5)
    Double rateStars = 0.0;
    Integer numberOfRatings = 0;

    @ManyToMany(mappedBy = "markedSpecialists")
    Set<Customer> marks;

    public Specialist() {
    }

    public Specialist(String name,String surname,Province province, String city, String profession, String phoneNumber,String mail,Double rateStars,Integer numberOfRatings) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.city = city;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.rateStars = rateStars;
        this.numberOfRatings = numberOfRatings;
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

    public Double getRateStars() {
        return rateStars;
    }

    public void setRateStars(Double rateStars) {
        this.rateStars = rateStars;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public Set<Customer> getMarks() {
        return marks;
    }

    public void setMarks(Set<Customer> marks) {
        this.marks = marks;
    }
}
