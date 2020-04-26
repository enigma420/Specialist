package pl.specialist.searchexpert.domains.customer;

import org.hibernate.annotations.GenericGenerator;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String customerId;
    @NotBlank(message = "Nickname may not be blank")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 32 characters")
    private String nickname;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City must be between 2 and 32 characters")
    private String city;
    @NotBlank(message = "Phone Number may not be blank")
    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;
    @NotBlank(message = "Email may not be blank")
    @Email
    private String mail;

    @ManyToMany
    @JoinTable(
    name = "specialists_mark",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "specialist_id")
    )
    private Set<Specialist> markedSpecialists;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER,mappedBy = "customer", orphanRemoval = true)
    private Set<Commission> commissions;

    public Customer() {
    }

    public Customer(String customerId, String nickname, String city, String phoneNumber, String mail){
        this.customerId = customerId;
        this.nickname = nickname;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public Customer( String nickname, String city, String phoneNumber, String mail){
        this.nickname = nickname;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public Set<Specialist> getMarkedSpecialists() {
        return markedSpecialists;
    }

    public void setMarkedSpecialists(Set<Specialist> markedSpecialists) {
        this.markedSpecialists = markedSpecialists;
    }

    public Set<Commission> getCommissions() {
        return commissions;
    }

    public void setCommissions(Set<Commission> commissions) {
        this.commissions = commissions;
    }
}
