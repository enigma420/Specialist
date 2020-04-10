package pl.specialist.searchexpert.domains.specialist;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Specialist {
    @Id
    @GeneratedValue
    Long id;

    String name;

    String surname;

    Province province;

    String city;

    @ElementCollection
    @CollectionTable(name = "listOfProfession")
    List<String> profession = new ArrayList<>();

    @Pattern(regexp="(^$|[0-9]{9})||(^$|[0-9]{11})")
    String phoneNumber;

    public Specialist() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getProfession() {
        return profession;
    }

    public void setProfession(List<String> profession) {
        this.profession = profession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
