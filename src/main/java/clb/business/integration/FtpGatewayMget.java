package clb.business.integration;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface FtpGatewayMget {

	public void mGet(@Header("pathName") String directory, @Payload String exp);
}
