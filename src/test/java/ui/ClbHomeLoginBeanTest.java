package ui;

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

}
