package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import org.springframework.stereotype.Component;

import clb.business.SimpleService;
import clb.database.entities.DataLogger;

@Component
@ViewScoped
@ManagedBean
public class SimpleBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{simpleService}")
	private SimpleService simpleService;
	
	@PostConstruct
	public void init(){
	}
	
	public void createDataLogger(){
	    
	    DataLogger dataLogger = new DataLogger();
	    dataLogger.setItemid( 1L );
	    dataLogger.setAl1( 1.0 );
	    
	    simpleService.createDataLogger(dataLogger);
	    
	    System.out.println( "Data Logger created successfully!" );
	}

    public SimpleService getSimpleService() {
        return simpleService;
    }

    public void setSimpleService( SimpleService simpleService ) {
        this.simpleService = simpleService;
    }
}
