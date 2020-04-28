package pl.specialist.searchexpert.domains.specialist;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;
import pl.specialist.searchexpert.domains.customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "specialist")
public class Specialist {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String specialistId;
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters")
    private String surname;
    @NotNull(message = "Province may not be blank")
    private Province province;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City must be between 2 and 25 characters")
    private String city;
    @NotBlank(message = "profession may not be blank")
    @Size(min = 3, max = 25, message = "Profession must be between 2 and 25 characters")
    private String profession;
    @NotBlank(message = "Profession may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    private String mail;
    @Min(0)
    @Max(5)
    private Double rateStars = 0.0;
    private Integer numberOfRatings = 0;

    @ManyToMany
    private Set<Customer> marks;

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


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
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

//    public void setMail(String mail) {
//        this.mail = mail;
//    }

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
