package pl.specialist.searchexpert.services.customer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;
import pl.specialist.searchexpert.services.specialist.SpecialistServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CustomerServiceImplIntegrationTest {

    @TestConfiguration
    static class CustomerServiceImplTestContextConfiguration{
        @Bean
        public CustomerService customerService(){
            return new CustomerServiceImpl(customerRepo,specialistRepo);
        }
    }

    @Autowired
    private static CustomerServiceImpl customerServiceImpl;

    @MockBean
    private static CustomerRepo customerRepo;

    @MockBean
    private static SpecialistRepo specialistRepo;

    @Before
    public void setUp(){
        Customer first_customer= new Customer( "Hrabia", "Warszawa", "795034234","danke1@op.pl");

        Mockito.when(customerRepo.findByNickname(first_customer.getNickname())).thenReturn(first_customer);
        Mockito.when(customerRepo.findByMail(first_customer.getMail())).thenReturn(first_customer);
    }

    @Test
    public void whenValidNickname_thenCustomerShouldBeFound(){
        String name = "Hrabia";
        Customer found = customerServiceImpl.findCustomerByNickname(name);
        assertThat(found.getNickname()).isEqualTo(name);
    }

    @Test
    public void whenValidMail_thenCustomerShouldBeFound(){
        String mail = "danke1@op.pl";
        Customer found = customerServiceImpl.findCustomerByMail(mail);
        assertThat(found.getMail()).isEqualTo(mail);
    }


}