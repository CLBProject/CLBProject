package clb.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.database.ClbDao;
import clb.database.entities.DataLogger;

@Service
public class SimpleServiceImpl implements SimpleService{

	@Autowired
	private ClbDao<DataLogger> clbDao;

	@Transactional
	public void createDataLogger(DataLogger dataLogger) {
		clbDao.create( dataLogger );
	}


	public ClbDao<DataLogger> getClbDao() {
		return clbDao;
	}

	public void setClbDao( ClbDao<DataLogger> clbDao ) {
		this.clbDao = clbDao;
	}


}
