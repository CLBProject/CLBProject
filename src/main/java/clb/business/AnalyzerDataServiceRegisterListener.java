package clb.business;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import clb.business.objects.UsersystemObject;

@Component
public class AnalyzerDataServiceRegisterListener implements
  ApplicationListener<AnalyzerDataServiceRegisterOnCompleteEvent> {
  
    @Autowired
    private AnalyzerDataService analyzerDataService;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
    public void onApplicationEvent(AnalyzerDataServiceRegisterOnCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(AnalyzerDataServiceRegisterOnCompleteEvent event) {
        UsersystemObject user = event.getUser();
        String token = UUID.randomUUID().toString();
        user.setToken( token );
        analyzerDataService.saveObject(user);
         
        String recipientAddress = user.getUsername();
        String subject = "Registration Confirmation";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText( "Access Link @ " + event.getAppUrl() + token);
        mailSender.send( email );
    }

    public AnalyzerDataService getAnalyzerDataService() {
        return analyzerDataService;
    }

    public void setAnalyzerDataService( AnalyzerDataService analyzerDataService ) {
        this.analyzerDataService = analyzerDataService;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender( JavaMailSender mailSender ) {
        this.mailSender = mailSender;
    }
    
    
}
