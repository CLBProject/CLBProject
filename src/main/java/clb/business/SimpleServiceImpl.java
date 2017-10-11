package clb.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clb.database.ClbDao;

@Service
public class SimpleServiceImpl implements SimpleService{

    @Autowired
    private ClbDao clbDao;
    
	@Override
	public void testService() {
		
	}

    public ClbDao getClbDao() {
        return clbDao;
    }

    public void setClbDao( ClbDao clbDao ) {
        this.clbDao = clbDao;
    }

	
}
