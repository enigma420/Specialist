package pl.specialist.searchexpert.domains;

import pl.specialist.searchexpert.domains.specialist.Specialist;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class SpecialistConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(targetEntity = Specialist.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "specialist_id")
    private Specialist specialist;

    public SpecialistConfirmationToken() {
    }

    public SpecialistConfirmationToken(Specialist specialist) {
        this.specialist = specialist;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }

    public long getTokenid() {
        return tokenid;
    }

    public void setTokenid(long tokenid) {
        this.tokenid = tokenid;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }
}
