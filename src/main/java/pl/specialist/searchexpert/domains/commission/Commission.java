package pl.specialist.searchexpert.domains.commission;


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
    String commissionId;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Customer author;
    @NotBlank(message = "title may not be blank")
    @Size(min = 5, max = 60 , message = "Title must be between 5 and 60 characters")
    String title;
    @NotBlank(message = "description may not be blank")
    @Size(min = 5, max = 256 , message = "Title must be between 5 and 256 characters")
    String description;

    public Commission() {
    }

    public String getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(String commissionId) {
        this.commissionId = commissionId;
    }

    public Customer getAuthor() {
        return author;
    }

    public void setAuthor(Customer author) {
        this.author = author;
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
}
