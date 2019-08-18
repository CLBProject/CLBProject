package ui;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;

import clb.ui.beans.ClbHomeLoginBean;

public class ClbHomeLoginBeanTest extends AbstractBeanTest{

	@InjectMocks
	ClbHomeLoginBean clbHomeLoginBean;
	
	@Override
	public Object getBean() {
		return clbHomeLoginBean;
	}

	@Override
	public void initBean() {
		clbHomeLoginBean.init();
	}

	@Override
	public Set<String> getExecptions() {
		Set<String> exceptions = new HashSet<String>();
		
		exceptions.add("userUiPojo");
		
		return exceptions;
	}

}
