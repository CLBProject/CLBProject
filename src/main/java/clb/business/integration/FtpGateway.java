package clb.business.integration;

import org.springframework.messaging.handler.annotation.Header;

public interface FtpGateway {

	public void read(String filename, @Header("pathname") String dirToCreate);
}
