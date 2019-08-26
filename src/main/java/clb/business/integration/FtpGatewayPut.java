package clb.business.integration;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface FtpGatewayPut {

	void upload(@Header("pathName") String directory, @Header("fileName") String fileName, @Payload String path);
}
