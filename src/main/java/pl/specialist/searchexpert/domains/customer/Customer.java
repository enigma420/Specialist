package pl.specialist.searchexpert.domains.customer;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.specialist.searchexpert.domains.commission.Commission;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "customer_id",updatable = false)
    private String customerId;
    @NotBlank(message = "Nickname may not be blank")
    @Size(min = 3, max = 20, message = "Nickname '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "nickname",updatable = false)
    private String nickname;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "city")
    private String city;
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

    @ManyToMany
    private Set<Specialist> markedSpecialists;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,mappedBy = "customer")
    private Set<Commission> commissions;

    public Customer() {
    }

    public Customer(String nickname, String city, String phoneNumber, String mail){
        this.nickname = nickname;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public Customer(String nickname, String city, String phoneNumber, String mail,String password){
        this.nickname = nickname;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
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
//
    public Set<Commission> getCommissions() {
        return commissions;
    }

    public void setCommissions(Set<Commission> commissions) {
        this.commissions = commissions;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("CUSTOMER"));
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
        return true;
    }
}
