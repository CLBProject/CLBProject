package clb.business;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.AnalyzerRegistryObject;
import clb.database.ClbDao;
import clb.database.entities.AnalyzerRegistryEntity;

@Service
@Component
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ClbDao<AnalyzerRegistryEntity> clbDaoAnalyzer;

	@Value(value = "classpath:documents/fileAnalyzerRegistries.xlsx")
	private Resource dataAnalyzerXls;

	public List<AnalyzerRegistryObject> getAnalyzerGraphicalData() throws IOException{

		return clbDaoAnalyzer.getAllAnalyzerRegistryData().stream()
					.map(AnalyzerRegistryObject::new)
					.collect(Collectors.toList());
	}


	@Override
	@Transactional
	public void fillDatabaseData() throws IOException{
		
		XSSFWorkbook workbook = new XSSFWorkbook(dataAnalyzerXls.getInputStream());
		XSSFSheet worksheet = workbook.getSheet("Sheet1");

		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(new Date());
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currentMinute = calendar.get(Calendar.MINUTE);

		List<AnalyzerRegistryEntity> analyzerRegistries = new ArrayList<AnalyzerRegistryEntity>();
		List<AnalyzerRegistryEntity> analyzerRegistriesBeforeCurrentTime = new ArrayList<AnalyzerRegistryEntity>();

		boolean currentHourPassed = false;
		boolean currentMinutesPassed = false;

		for(int i = 1;i<worksheet.getLastRowNum();i++){

			XSSFRow row = worksheet.getRow(i);

			AnalyzerRegistryObject analyzerRegistryObject = new AnalyzerRegistryObject();
			analyzerRegistryObject.setCurrentdate(row.getCell(0).getDateCellValue());

			calendar.setTime(row.getCell(1).getDateCellValue());   // assigns calendar to given date 

			if(calendar.get(Calendar.HOUR_OF_DAY) == currentHour){
				currentHourPassed = true;
				if(calendar.get(Calendar.MINUTE) == currentMinute){
					currentMinutesPassed = true;
				}
			}

			analyzerRegistryObject.setCurrenttime((calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY)+"") + ":" + 
					(calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)+"") +":"+ 
					(calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND) : calendar.get(Calendar.SECOND)));

			analyzerRegistryObject.setAl1(row.getCell(2).getNumericCellValue());
			analyzerRegistryObject.setAl2(row.getCell(3).getNumericCellValue());
			analyzerRegistryObject.setAl3(row.getCell(4).getNumericCellValue());
			analyzerRegistryObject.setHz(row.getCell(5).getNumericCellValue());
			analyzerRegistryObject.setPfl1(row.getCell(6).getNumericCellValue());
			analyzerRegistryObject.setPfl2(row.getCell(7).getNumericCellValue());
			analyzerRegistryObject.setPfl3(row.getCell(8).getNumericCellValue());
			analyzerRegistryObject.setPfsys(row.getCell(9).getNumericCellValue());
			//analyzerRegistryObject.setPhaseSequence(row.getCell(10).getNumericCellValue());
			analyzerRegistryObject.setVl1l2(row.getCell(11).getNumericCellValue());
			analyzerRegistryObject.setVl1n(row.getCell(12).getNumericCellValue());
			analyzerRegistryObject.setVl2l3(row.getCell(13).getNumericCellValue());
			analyzerRegistryObject.setVl2n(row.getCell(14).getNumericCellValue());
			analyzerRegistryObject.setVl3l1(row.getCell(15).getNumericCellValue());
			analyzerRegistryObject.setVl3n(row.getCell(16).getNumericCellValue());
			analyzerRegistryObject.setVllsys(row.getCell(17).getNumericCellValue());
			analyzerRegistryObject.setVlnsys(row.getCell(18).getNumericCellValue());
			analyzerRegistryObject.setKval1(row.getCell(19).getNumericCellValue());
			analyzerRegistryObject.setKval2(row.getCell(20).getNumericCellValue());
			analyzerRegistryObject.setKval3(row.getCell(21).getNumericCellValue());
			analyzerRegistryObject.setKvasys(row.getCell(22).getNumericCellValue());
			analyzerRegistryObject.setKwh(row.getCell(23).getNumericCellValue());
			analyzerRegistryObject.setKwl1(row.getCell(24).getNumericCellValue());
			analyzerRegistryObject.setKwl2(row.getCell(25).getNumericCellValue());
			analyzerRegistryObject.setKwl3(row.getCell(26).getNumericCellValue());
			analyzerRegistryObject.setKwsys(row.getCell(27).getNumericCellValue());
			analyzerRegistryObject.setKvarh(row.getCell(28).getNumericCellValue());
			analyzerRegistryObject.setKvarl1(row.getCell(29).getNumericCellValue());
			analyzerRegistryObject.setKvarl2(row.getCell(30).getNumericCellValue());
			analyzerRegistryObject.setKvarl3(row.getCell(31).getNumericCellValue());
			analyzerRegistryObject.setKvarsys(row.getCell(32).getNumericCellValue());
			
			if(currentHourPassed && currentMinutesPassed)
				analyzerRegistriesBeforeCurrentTime.add(analyzerRegistryObject.toEntity());
			else analyzerRegistries.add(analyzerRegistryObject.toEntity());
		}
		analyzerRegistriesBeforeCurrentTime.addAll(analyzerRegistries);
		
		clbDaoAnalyzer.persistData(analyzerRegistriesBeforeCurrentTime);
	}

	public ClbDao<AnalyzerRegistryEntity> getClbDaoAnalyzer() {
		return clbDaoAnalyzer;
	}

	public void setClbDaoAnalyzer(ClbDao<AnalyzerRegistryEntity> clbDaoAnalyzer) {
		this.clbDaoAnalyzer = clbDaoAnalyzer;
	}

	public Resource getDataAnalyzerXls() {
		return dataAnalyzerXls;
	}

	public void setDataAnalyzerXls(Resource dataAnalyzerXls) {
		this.dataAnalyzerXls = dataAnalyzerXls;
	}
}
