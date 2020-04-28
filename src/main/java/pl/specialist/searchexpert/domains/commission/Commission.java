package pl.specialist.searchexpert.domains.commission;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import pl.specialist.searchexpert.domains.customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "commission")
public class Commission {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String commissionId;
    @NotBlank(message = "title may not be blank")
    @Size(min = 5, max = 60 , message = "Title must be between 5 and 60 characters")
    private String title;
    @NotBlank(message = "description may not be blank")
    @Size(min = 5, max = 256 , message = "Title must be between 5 and 256 characters")
    private String description;
    @NotBlank(message = "City may not be blank")
    @Size(min = 3, max = 25, message = "City must be between 2 and 25 characters")
    private String city;
    @NotBlank(message = "Profession may not be blank")
    @Size(min = 3,max = 25,message = "Profession must be between 2 and 25 characters")
    private String profession;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    private String commissionAuthorNickname;

    public Commission() {
    }


    public String getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(String commissionId) {
        this.commissionId = commissionId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCommissionAuthorNickname() {
        return commissionAuthorNickname;
    }

    public void setCommissionAuthorNickname(String commissionAuthorNickname) {
        this.commissionAuthorNickname = commissionAuthorNickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
