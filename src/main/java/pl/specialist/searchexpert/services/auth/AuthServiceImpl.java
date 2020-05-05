package pl.specialist.searchexpert.services.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.CustomerConfirmationToken;
import pl.specialist.searchexpert.domains.SpecialistConfirmationToken;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;
import pl.specialist.searchexpert.exceptions.register.AlreadyExistUserFieldException;
import pl.specialist.searchexpert.exceptions.register.InvalidConfirmTokenException;
import pl.specialist.searchexpert.exceptions.register.RegisterConfirmFieldException;
import pl.specialist.searchexpert.exceptions.register.UnconfirmedAccountException;
import pl.specialist.searchexpert.payload.login.LoginRequest;
import pl.specialist.searchexpert.repositories.CustomerConfirmationTokenRepo;
import pl.specialist.searchexpert.repositories.SpecialistConfirmationTokenRepo;
import pl.specialist.searchexpert.repositories.customer.CustomerRepo;
import pl.specialist.searchexpert.repositories.specialist.SpecialistRepo;
import pl.specialist.searchexpert.security.JwtTokenProvider;
import pl.specialist.searchexpert.services.EmailSenderService;

import static pl.specialist.searchexpert.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    private final SpecialistRepo specialistRepo;

    private final CustomerRepo customerRepo;

    private final EmailSenderService emailSenderService;

    private final SpecialistConfirmationTokenRepo specialistConfirmationTokenRepo;

    private final CustomerConfirmationTokenRepo customerConfirmationTokenRepo;


    public AuthServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, SpecialistRepo specialistRepo, CustomerRepo customerRepo, EmailSenderService emailSenderService, SpecialistConfirmationTokenRepo specialistConfirmationTokenRepo, CustomerConfirmationTokenRepo customerConfirmationTokenRepo) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.specialistRepo = specialistRepo;
        this.customerRepo = customerRepo;
        this.emailSenderService = emailSenderService;
        this.specialistConfirmationTokenRepo = specialistConfirmationTokenRepo;
        this.customerConfirmationTokenRepo = customerConfirmationTokenRepo;
    }

    @Override
    public String login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getMail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken;
        if(customerRepo.findByMail(loginRequest.getMail()) == null){
            if(specialistRepo.findByMail(loginRequest.getMail()).isEnabledToUse()) jwtToken = TOKEN_PREFIX + tokenProvider.generateTokenForSpecialist(authentication);
            else throw new UnconfirmedAccountException("Specialist Please confirm you account by token from mail");
        }else {
                if(customerRepo.findByMail(loginRequest.getMail()).isEnabledToUse()) jwtToken = TOKEN_PREFIX + tokenProvider.generateTokenForCustomer(authentication);
                else throw new UnconfirmedAccountException("Customer Please confirm you account by token from mail");
        }
        return jwtToken;
    }

    @Override
    public Customer registerCustomerAccount(Customer cust){
        if(customerRepo.existsByMail(cust.getMail())) throw new AlreadyExistUserFieldException("Already exist Customer with this mail !");
        if(customerRepo.existsByNickname(cust.getNickname())) throw new AlreadyExistUserFieldException("Already exist Customer with this Nickname !");
        if(customerRepo.existsByPhoneNumber(cust.getPhoneNumber())) throw new AlreadyExistUserFieldException("Already exist Customer with this Phone Number !");
        if(!cust.getMail().equals(cust.getConfirmMail())) throw new RegisterConfirmFieldException("Mail and Confirm Mail fields are not equals bebebe");
        if(!cust.getPassword().equals(cust.getConfirmPassword())) throw new RegisterConfirmFieldException("Password and Confirm Password fields are not equals bebebe");
        Customer customer = new Customer(cust.getNickname(),
                cust.getCity(),cust.getPhoneNumber(),
                cust.getMail(),cust.getPassword());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);

        CustomerConfirmationToken confirmationToken = new CustomerConfirmationToken(customer);

        customerConfirmationTokenRepo.save(confirmationToken);

        try{
            emailSenderService.sendNotificationMailToCustomer(customer,confirmationToken);
        }catch (Exception e){

        }


        return customer;
    }

    @Override
    public Specialist registerSpecialistAccount(Specialist spec){
        if(specialistRepo.existsByMail(spec.getMail())) throw new AlreadyExistUserFieldException("Already exist Specialist with this mail !");
        if(specialistRepo.existsByPhoneNumber(spec.getPhoneNumber())) throw new AlreadyExistUserFieldException("Already exist Specialist with this Phone Number !");
        if(!spec.getMail().equals(spec.getConfirmMail())) throw new RegisterConfirmFieldException("Mail and Confirm Mail fields are not equals");
        if(!spec.getPassword().equals(spec.getConfirmPassword())) throw new RegisterConfirmFieldException("Password and Confirm Password fields are not equals");
        Specialist specialist = new Specialist(spec.getName(),
                spec.getSurname(),spec.getProvince(),
                spec.getCity(),spec.getProfession(),
                spec.getPhoneNumber(),spec.getMail(),
                spec.getPassword());
        specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
        specialistRepo.save(specialist);


        SpecialistConfirmationToken confirmationToken = new SpecialistConfirmationToken(specialist);

        specialistConfirmationTokenRepo.save(confirmationToken);

        try{
            emailSenderService.sendNotificationMailToSpecialist(specialist,confirmationToken);
        }catch (Exception e){

        }

        return specialist;
    }

    public void confirmCustomerAccount(String confirmationToken){
        CustomerConfirmationToken token = customerConfirmationTokenRepo.findByConfirmationToken(confirmationToken);

        if(token != null){
            Customer customer = customerRepo.findByMail(token.getCustomer().getMail());
            customer.setEnabledToUse(true);
            customerRepo.save(customer);
        }else throw new InvalidConfirmTokenException("This confirm token is wrong !");
    }

    public void confirmSpecialistAccount(String confirmationToken){
        SpecialistConfirmationToken token = specialistConfirmationTokenRepo.findByConfirmationToken(confirmationToken);

        if(token != null){
            Specialist specialist = specialistRepo.findByMail(token.getSpecialist().getMail());
            specialist.setEnabledToUse(true);
            specialistRepo.save(specialist);
        }else throw new InvalidConfirmTokenException("This confirm token is wrong !");
    }

}
