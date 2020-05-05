package pl.specialist.searchexpert.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.specialist.searchexpert.domains.CustomerConfirmationToken;

@Repository
public interface CustomerConfirmationTokenRepo extends JpaRepository<CustomerConfirmationToken,String> {

   CustomerConfirmationToken findByConfirmationToken(String confirmationToken);

}
