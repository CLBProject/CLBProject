package clb.business;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import clb.business.objects.AnalyzerObject;
import clb.business.FtpService;
@Service
public class FtpServiceImpl implements FtpService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() {

	}

	@Override
	public boolean userHasAccount(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AnalyzerObject> getAnalyzersFromUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Bean
	@ServiceActivator(inputChannel = "ftpChannel")
	public MessageHandler handler() {
		return new MessageHandler() {

			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				System.out.println(message.getPayload());
			}

		};
	}
}
