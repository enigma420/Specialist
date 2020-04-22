package pl.specialist.searchexpert.repositories;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.specialist.searchexpert.domains.customer.Customer;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@Transactional
@DataJpaTest
public class CustomerRepoUnitTest {

    @Autowired
    private CustomerRepo customerRepo;
    /*Five various specialists helpful to implement tests*/

    private static final Customer first_customer= new Customer( "Hrabia", "Warszawa", "795034234","danke1@op.pl");
    private static final Customer second_customer = new Customer( "Magneto", "Katowice", "503942034","enigma532@op.pl");
    private static final Customer third_customer= new Customer( "tiboun", "Poznań", "593032123","enigma32@op.pl");
    private static final Customer fourth_customer = new Customer( "Leonidas", "Warszawa", "739403593","danke123@op.pl");
    private static final Customer fifth_customer= new Customer( "Herkules", "Opole","512493053","danke12@op.pl");

    /*Searching a group of specialists at once*/
    private static final List<Customer> groupOfCustomers = new ArrayList<>();

    @BeforeClass
    public static void initializeSpecialistsIntoRepository() {
        // given
        groupOfCustomers.add(first_customer);
        groupOfCustomers.add(second_customer);
        groupOfCustomers.add(third_customer);
        groupOfCustomers.add(fourth_customer);
        groupOfCustomers.add(fifth_customer);
    }

    @BeforeEach
    public void setUp(){
        customerRepo.saveAll(groupOfCustomers);
    }

    @Test
    public void whenSaveCustomer_thenIsNotNull() {
        customerRepo.save(first_customer);
        assertThat(customerRepo.findAll()).isNotNull();
    }

    @Test
    public void whenDeleteAllCustomers_thenReturnEmptyRepo() {
        customerRepo.saveAll(groupOfCustomers);
        assertThat(customerRepo.findAll()).isNotEmpty();
        customerRepo.deleteAll();
        assertThat(customerRepo.findAll()).isEmpty();
    }

    @Test
    public void whenDeleteCustomerByCustomerId_thenCannotFindCustomerByCustomerId(){
        customerRepo.saveAll(groupOfCustomers);
        String specialistIdOfSpecialistWhichWillDelete = first_customer.getCustomerId();
        assertThat(customerRepo.count()).isEqualTo(5);
        customerRepo.delete(first_customer);
        assertThat(customerRepo.findByCustomerId(specialistIdOfSpecialistWhichWillDelete)).isNotEqualTo(first_customer);
    }

    @Test
    public void whenFindAllCustomers_thenReturnRepositorySize() {
        customerRepo.saveAll(groupOfCustomers);
        assertThat(customerRepo.count()).isEqualTo(5);
    }

    @Test
    public void whenFindCustomersByNickname_thenReturnCustomer() {
        customerRepo.saveAll(groupOfCustomers);
        Customer customer = customerRepo.findByNickname("Herkules");

        assertThat(customerRepo.findByNickname(customer.getNickname()).getNickname()).isEqualTo("Herkules");
    }

    @Test
    public void whenFindCustomerByMail_thenReturnCustomerWithThisMail(){
        customerRepo.saveAll(groupOfCustomers);
        String mailOfFirstSpecialist = groupOfCustomers.get(0).getMail();

        assertThat(customerRepo.findByMail(mailOfFirstSpecialist).getMail()).isEqualTo(first_customer.getMail());
    }

}