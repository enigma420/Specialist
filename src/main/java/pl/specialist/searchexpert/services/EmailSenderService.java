package pl.specialist.searchexpert.services;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.specialist.searchexpert.domains.CustomerConfirmationToken;
import pl.specialist.searchexpert.domains.SpecialistConfirmationToken;
import pl.specialist.searchexpert.domains.customer.Customer;
import pl.specialist.searchexpert.domains.specialist.Specialist;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private void sendEmail(SimpleMailMessage mail){
        javaMailSender.send(mail);
    }


    @Async
    public void sendNotificationMailToSpecialist(Specialist specialist, SpecialistConfirmationToken confirmationToken) throws MailException,InterruptedException{

        System.out.println("Sending email...");
        long startTime = System.currentTimeMillis();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(specialist.getMail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("specjalisto@gmail.com");
        mailMessage.setText("Hello Specialist ! \nTo confirm your account, please click here : \n"
                +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

        sendEmail(mailMessage);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Mail sent in time:" + elapsedTime);

    }

    @Async
    public void sendNotificationMailToCustomer(Customer customer, CustomerConfirmationToken confirmationToken) throws MailException,InterruptedException{

        System.out.println("Sending email...");
        long startTime = System.currentTimeMillis();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(customer.getMail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("specjalisto@gmail.com");
        mailMessage.setText("Hello Customer ! \nTo confirm your account, please click here : \n"
                +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

        sendEmail(mailMessage);
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Mail sent in time:" + elapsedTime);
    }

    /*
    TODO
    1.resend confirmation mail
    2.change password
    */
}
