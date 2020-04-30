package pl.specialist.searchexpert.domains.opinion;


import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "opinion")
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opinionId;
    @NotBlank(message = "Name may not be blank")
    @Size(min = 3, max = 100, message = "Name must be between 2 and 20 characters")
    private String title;
    @NotBlank(message = "Surname may not be blank")
    @Size(min = 3, max = 255, message = "Name must be between 2 and 20 characters")
    private String description;
    @NotNull(message = "Province may not be blank")
    private Integer rate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;


    public Opinion() {
    }

    public Long getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(Long opinionId) {
        this.opinionId = opinionId;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
}
