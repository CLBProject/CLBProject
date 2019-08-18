package ui;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;

import clb.ui.beans.ClbHomeRegisterBean;

public class ClbHomeRegisterBeanTest extends AbstractBeanTest{

	@InjectMocks
	ClbHomeRegisterBean clbHomeRegisterBean;
	
	@Override
	public Object getBean() {
		return clbHomeRegisterBean;
	}

	@Override
	public void initBean() {
		clbHomeRegisterBean.init();
	}

	@Override
	public Set<String> getExecptions() {
		return new HashSet<String>();
	}

}
