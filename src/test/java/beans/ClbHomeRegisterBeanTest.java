package beans;

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

}
