package clb.business.integration;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface FtpGateway {

	public void write(@Header("fileName")String fileName,@Payload String message);
	
	public void read(String filename);
}
