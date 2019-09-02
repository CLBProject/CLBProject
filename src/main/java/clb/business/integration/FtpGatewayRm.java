package clb.business.integration;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface FtpGatewayRm {

	void remove(@Header("file_remoteDirectory") String directory);
}
