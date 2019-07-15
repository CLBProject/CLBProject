package clb.business.integration;

import javax.faces.context.FacesContext;

import org.springframework.messaging.Message;

public class FtpPrinter {

	public void print(Message<?> message) {
		Object bean = FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("#{buildingManagementBean}");
		System.out.println(bean);
		System.out.println("Message Received:" + message);
	}
}
