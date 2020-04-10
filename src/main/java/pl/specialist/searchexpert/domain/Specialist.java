package pl.specialist.searchexpert.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Specialist {
    @Id
    @GeneratedValue
    Long id;

}
