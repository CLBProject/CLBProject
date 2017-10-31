package clb.business;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.AnalyzerRegistryObject;
import clb.database.ClbDao;
import clb.database.entities.DataLogger;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

	@Autowired
	private ClbDao<DataLogger> clbDao;

	@Value(value = "classpath:documents/fileAnalyzerRegistries.xlsx")
	private Resource dataAnalyzerXls;

	@Transactional
	public void createDataLogger(DataLogger dataLogger) {
		clbDao.create( dataLogger );
	}

	public List<AnalyzerRegistryObject> getAnalyzerGraphicalData() throws IOException{

		XSSFWorkbook workbook = new XSSFWorkbook(dataAnalyzerXls.getInputStream());
		XSSFSheet worksheet = workbook.getSheet("Sheet1");

		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(new Date());
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

		List<AnalyzerRegistryObject> analyzerRegistries = new ArrayList<AnalyzerRegistryObject>();
		List<AnalyzerRegistryObject> analyzerRegistriesBeforeCurrentTime = new ArrayList<AnalyzerRegistryObject>();

		boolean currentHourPassed = false;

		for(int i = 1;i<worksheet.getLastRowNum();i++){

			XSSFRow row = worksheet.getRow(i);

			AnalyzerRegistryObject analyzerRegistryObject = new AnalyzerRegistryObject();
			analyzerRegistryObject.setCurrentdate(row.getCell(0).getDateCellValue());

			calendar.setTime(row.getCell(1).getDateCellValue());   // assigns calendar to given date 

			if(calendar.get(Calendar.HOUR_OF_DAY) == currentHour){
				currentHourPassed = true;
			}

			analyzerRegistryObject.setCurrenttime((calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY)+"") + ":" + 
					(calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)+"") +":"+ 
					(calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND) : calendar.get(Calendar.SECOND)));

			analyzerRegistryObject.setAl1(row.getCell(2).getNumericCellValue());
			analyzerRegistryObject.setAl2(row.getCell(3).getNumericCellValue());
			analyzerRegistryObject.setAl3(row.getCell(4).getNumericCellValue());
			analyzerRegistryObject.setKwh(row.getCell(5).getNumericCellValue());

			if(currentHourPassed)
				analyzerRegistriesBeforeCurrentTime.add(analyzerRegistryObject);
			else analyzerRegistries.add(analyzerRegistryObject);
		}

		//Add Missing First Registries
		analyzerRegistriesBeforeCurrentTime.addAll(analyzerRegistries);

		return analyzerRegistriesBeforeCurrentTime;
	}

	public ClbDao<DataLogger> getClbDao() {
		return clbDao;
	}

	public void setClbDao( ClbDao<DataLogger> clbDao ) {
		this.clbDao = clbDao;
	}

	public Resource getDataAnalyzerXls() {
		return dataAnalyzerXls;
	}

	public void setDataAnalyzerXls(Resource dataAnalyzerXls) {
		this.dataAnalyzerXls = dataAnalyzerXls;
	}


}
