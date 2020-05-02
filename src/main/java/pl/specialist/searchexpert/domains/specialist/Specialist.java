package pl.specialist.searchexpert.domains.specialist;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;
import org.hibernate.validator.constraints.Range;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.opinion.Opinion;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.Queue;

@Entity
@Table(name = "specialist")
public class Specialist {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "specialist_id",updatable = false)
    private String specialistId;
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 20, message = "Name '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "name",updatable = false)
    private String name;
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 20, message = "Name '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "surname",updatable = false)
    private String surname;
    @NotNull(message = "Province may not be blank")
    @Column(name = "province")
    private Province province;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "city")
    private String city;
    @NotBlank(message = "Profession may not be blank")
    @Size(min = 3, max = 25, message = "Profession '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "profession")
    private String profession;
    @NotBlank(message = "Phone Number may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    @Column(name = "phone_number",updatable = false)
    private String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    @Column(name = "mail",updatable = false)
    private String mail;
    @NotBlank(message = "Password may not be blank")
    @Size(min = 5 , max = 30, message = "Password '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "password")
    private String password;
    @Column(name = "rate")
    private HashMap<String,Double> rateStars;
    @Range(min = 0 , max = 5)
    @Column(name = "average_rate")
    private Double averageRate;

    public Specialist() {
    }

    public Specialist(String name,String surname,Province province, String city, String profession, String phoneNumber,String mail,HashMap<String,Double> rateStars,Double  averageRate) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.city = city;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.rateStars = rateStars;
        this.averageRate = averageRate;
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

    public HashMap<String, Double> getRateStars() {
        return rateStars;
    }

    public void setRateStars(HashMap<String, Double> rateStars) {
        this.rateStars = rateStars;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }
}
