package clb.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import clb.business.objects.UserEvent;

@Component
public class UserRegistryServicePublisherEvent  implements ApplicationListener<UserEvent> {


    @Autowired 
    private ApplicationEventPublisher eventPublisher;
    
    
    @Autowired
    private MailSender mailSender;
    

	@Override
	public void onApplicationEvent(UserEvent event) {
		 String recipientAddress = event.getUserName();

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(event.getSubject());
        email.setText( event.getTextMsg());
        mailSender.send( email );
		
	}

	public void publishEvent(UserEvent userEvent) {
		eventPublisher.publishEvent(userEvent);
		
	}

	public ApplicationEventPublisher getEventPublisher() {
		return eventPublisher;
	}

	public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
}
