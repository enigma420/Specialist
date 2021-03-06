package pl.specialist.searchexpert.domains.specialist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.specialist.searchexpert.domains.customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "specialist")
public class Specialist implements UserDetails {
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
    @Transient
    private String confirmMail;
    @NotBlank(message = "Password may not be blank")
    @Size(min = 5 , message = "Password '${validatedValue}' isn't correct => must be between {min} characters")
    @Column(name = "password")
    private String password;
    @Transient
    private String confirmPassword;
    private Integer numberOfRatings = 0;
    @Range(min = 0 , max = 5)
    @Column(name = "average_rate")
    private Double averageRate = 0.0;
    private Double sumOfRatingsValue = 0.0;
    @Column(name = "profile_img")
    private String profileImg;
    private boolean enable;

    @JsonIgnore
    @ManyToMany
    private Set<Customer> markedByCustomers;

    public Specialist() {
    }



    public Specialist(String name,String surname,Province province, String city, String profession, String phoneNumber,String mail,Integer numberOfRatings,Double  averageRate) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.city = city;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.numberOfRatings = numberOfRatings;
        this.averageRate = averageRate;
    }


    public Specialist(String name,String surname,Province province, String city, String profession, String phoneNumber,String mail,String password) {
        this.name = name;
        this.surname = surname;
        this.province = province;
        this.city = city;
        this.profession = profession;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<Customer> getMarkedByCustomers() {
        return markedByCustomers;
    }

    public void setMarkedByCustomers(Set<Customer> markedByCustomers) {
        this.markedByCustomers = markedByCustomers;
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

    public Double getSumOfRatingsValue() {
        return sumOfRatingsValue;
    }

    public void setSumOfRatingsValue(Double sumOfRatingsValue) {
        this.sumOfRatingsValue = sumOfRatingsValue;
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Integer numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
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

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("SPECIALIST"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}
