package clb.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrySendEmailListener implements
  ApplicationListener<UserRegistrySendEmailEvent> {
  
    @Autowired
    private UserRegistryService userRegistryService;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
    public void onApplicationEvent(UserRegistrySendEmailEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(UserRegistrySendEmailEvent event) {

        String recipientAddress = event.getUserName();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(event.getSubject());
        email.setText( event.getTextMsg());
        mailSender.send( email );
    }

    public UserRegistryService getUserRegistryService() {
        return userRegistryService;
    }

    public void setUserRegistryService( UserRegistryService userRegistryService ) {
        this.userRegistryService = userRegistryService;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender( JavaMailSender mailSender ) {
        this.mailSender = mailSender;
    }
    
    
}
