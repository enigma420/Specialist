package pl.specialist.searchexpert.domains.opinion;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "opinion")
public class Opinion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "opinion_id",updatable = false)
    private String opinionId;
    @NotBlank(message = "Title may not be blank")
    @Size(min = 3, max = 30, message = "Title '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "title")
    private String title;
    @NotBlank(message = "Description may not be blank")
    @Size(min = 3, max = 255, message = "Description '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "description")
    private String description;
    @NotNull(message = "Rate may not be blank")
    @Range(min = 0 , max = 5 , message = "Rate '${validatedValue}' isn't correct => must be between {min} and {max} characters")
    @Column(name = "rate")
    private Double rate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;


    public Opinion() {
    }

    public String getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(String opinionId) {
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
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
