package clb.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Autowired;

import clb.business.SimpleService;

@ViewScoped
@ManagedBean
public class SimpleBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	SimpleService simpleService;
	
	@PostConstruct
	public void init(){
		
	}
}
