package clb.business.deprecated;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.AnalyzerMeterObject;
import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.DivisionObject;
import clb.business.objects.UsersystemObject;
import clb.database.ClbDao;
import clb.global.AnalyzerMeterValues;

@Service
public class AnalyzerDataServiceDeprecatedImpl implements AnalyzerDataServiceDeprecated, Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Value(value = "classpath:documents")
	private Resource dataAnalyzerXls;

	@Autowired
	private ClbDao clbDao;


	@Override
	@Transactional
	public void persistDataForUser(String userName) throws IOException{
		for(File file: dataAnalyzerXls.getFile().listFiles()){
			updateAnalyzerRegistriesForAnalyzer(file,clbDao.findUserByUserName( userName ));
		}
	}


	private void updateAnalyzerRegistriesForAnalyzer(File file, UsersystemObject userObject) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

		BuildingObject building = new BuildingObject();
		building.setName(file.getName().split("\\.")[0]);
		building.setBuildingusername(file.getName().split("\\.")[0]);
		building.setImgPath( "building1.jpg" );
		userObject.addBuilding(building);

		BuildingObject building2 = new BuildingObject();
		building2.setName("Dummy Byulding");
		building2.setBuildingusername(file.getName().split("\\.")[0]);
		building2.setImgPath( "building2.jpg" );
		userObject.addBuilding(building2);

		for(int j = 0; j<workbook.getNumberOfSheets();j++){

			DivisionObject divObj = new DivisionObject();
			AnalyzerObject ana = new AnalyzerObject();
			
			building.setMainDivision(divObj);
			divObj.addAnalyzer(ana);
			
			ana.setCodeName( "Analyzer " + (j+1));
			
			
			XSSFSheet worksheet = workbook.getSheetAt( j );

			Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance

			Set<Long> dataToExclueOnDummy = new HashSet<Long>();

			clbDao.saveAnalyzer(ana);

			List<AnalyzerRegistryObject> analyzerRegistries = new ArrayList<AnalyzerRegistryObject>();

			for(int i = 2;i<worksheet.getLastRowNum();i++){

				XSSFRow row = worksheet.getRow(i);

				if(row.getCell(0) == null){
					continue;
				}

				Date currentRowDate = row.getCell(0).getDateCellValue();
				String[] currentRowTime = row.getCell(1).getStringCellValue().split(":");

				calendar.setTime(currentRowDate);   // assigns calendar to given date 
				calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(currentRowTime[0]));
				calendar.set(Calendar.MINUTE, Integer.parseInt(currentRowTime[1]));

				dataToExclueOnDummy.add( calendar.getTime().getTime() );

				AnalyzerRegistryObject analyzerRegistryObject = new AnalyzerRegistryObject();

				analyzerRegistryObject.setCurrenttime( calendar.getTime() );
				analyzerRegistryObject.setAl1(row.getCell(2).getNumericCellValue());
				analyzerRegistryObject.setAl2(row.getCell(3).getNumericCellValue());
				analyzerRegistryObject.setAl3(row.getCell(4).getNumericCellValue());
				analyzerRegistryObject.setHz(row.getCell(5).getNumericCellValue());
				analyzerRegistryObject.setPfl1(row.getCell(6).getNumericCellValue());
				analyzerRegistryObject.setPfl2(row.getCell(7).getNumericCellValue());
				analyzerRegistryObject.setPfl3(row.getCell(8).getNumericCellValue());
				analyzerRegistryObject.setPfsys(row.getCell(9).getNumericCellValue());
				analyzerRegistryObject.setVl1l2(row.getCell(10).getNumericCellValue());
				analyzerRegistryObject.setVl1n(row.getCell(11).getNumericCellValue());
				analyzerRegistryObject.setVl2l3(row.getCell(12).getNumericCellValue());
				analyzerRegistryObject.setVl2n(row.getCell(13).getNumericCellValue());
				analyzerRegistryObject.setVl3l1(row.getCell(14).getNumericCellValue());
				analyzerRegistryObject.setVl3n(row.getCell(15).getNumericCellValue());
				analyzerRegistryObject.setVllsys(row.getCell(16).getNumericCellValue());
				analyzerRegistryObject.setVlnsys(row.getCell(17).getNumericCellValue());
				analyzerRegistryObject.setKval1(row.getCell(18).getNumericCellValue());
				analyzerRegistryObject.setKval2(row.getCell(19).getNumericCellValue());
				analyzerRegistryObject.setKval3(row.getCell(20).getNumericCellValue());
				analyzerRegistryObject.setKvasys(row.getCell(21).getNumericCellValue());
				analyzerRegistryObject.setKwl1(row.getCell(22).getNumericCellValue());
				analyzerRegistryObject.setKwl2(row.getCell(23).getNumericCellValue());
				analyzerRegistryObject.setKwl3(row.getCell(24).getNumericCellValue());
				analyzerRegistryObject.setKwsys(row.getCell(25).getNumericCellValue());
				analyzerRegistryObject.setKvarl1(row.getCell(26).getNumericCellValue());
				analyzerRegistryObject.setKvarl2(row.getCell(27).getNumericCellValue());
				analyzerRegistryObject.setKvarl3(row.getCell(28).getNumericCellValue());
				analyzerRegistryObject.setKvarsys(row.getCell(29).getNumericCellValue());
				analyzerRegistryObject.setAnalyzerId(ana.getId());

				analyzerRegistries.add( analyzerRegistryObject );
			}


			clbDao.saveAnalyzerRegistries(analyzerRegistries);

			analyzerRegistries.stream().forEach( analyzerRegistry -> ana.addAnalyzerRegistryId( analyzerRegistry.getId() ) );

			persistDummyAnalyzerRegistries( ana, dataToExclueOnDummy);

			for(AnalyzerMeterValues analyzerMeter: AnalyzerMeterValues.values()) {
				AnalyzerMeterObject analyzerMeterObject = new AnalyzerMeterObject();
				analyzerMeterObject.setName( analyzerMeter.getLabel() );
				analyzerMeterObject.setLabelKey( analyzerMeter.name() );
				analyzerMeterObject.setUnit(analyzerMeter.getUnit());

				clbDao.saveAnalyzerMeter( analyzerMeterObject );
				
				ana.addAnalyzerMeter(analyzerMeterObject);
			}
			
			clbDao.saveAnalyzer(ana);
		}

		clbDao.saveBuilding(building);
		clbDao.saveBuilding(building2);
		clbDao.saveUsersystem( userObject );
	}

	private void persistDummyAnalyzerRegistries(AnalyzerObject analyzer, Set<Long> dataToExclude){

		Random random = new Random();

		int numberOfYears = 1;
		int startingYear = 2018;
		int lowAl = 200;
		int highAl = 450;

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set( startingYear, 0, 1);

		System.out.println( "Starting persisting data... ");

		for(int a = 0 ;a<numberOfYears; a++){
			for(int l = 0; l < 12; l++ ){

				List<AnalyzerRegistryObject> analyzersRegistries = new ArrayList<AnalyzerRegistryObject>();

				int monthLength = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

				for(int m=0; m < monthLength; m++) {
					for(int b = 0; b < 24 ; b++){
						for(int k = 0; k < 60; k+=5){

							Calendar currentTimeCalendar = Calendar.getInstance();
							currentTimeCalendar.set(Calendar.YEAR, a+startingYear);
							currentTimeCalendar.set(Calendar.MONTH, l);
							currentTimeCalendar.set(Calendar.DAY_OF_MONTH, m+1);
							currentTimeCalendar.set(Calendar.HOUR_OF_DAY, b);
							currentTimeCalendar.set(Calendar.MINUTE, k);
							currentTimeCalendar.set(Calendar.SECOND, 0);

							Date time = currentTimeCalendar.getTime();

							if(dataToExclude.contains( time.getTime())){
								continue;
							}

							AnalyzerRegistryObject anaRegObj = new AnalyzerRegistryObject();

							anaRegObj.setCurrenttime( time);

							//Voltage
							anaRegObj.setVlnsys(lowAl + (highAl - lowAl) * random.nextDouble());

							//Voltage Between Phasys
							anaRegObj.setVllsys(lowAl + (highAl - lowAl) * random.nextDouble());

							//Power
							anaRegObj.setKwsys(lowAl + (highAl - lowAl) * random.nextDouble());

							//Reactive Power
							anaRegObj.setKvarsys( lowAl + (highAl - lowAl) * random.nextDouble() );

							//Volt-Ampere
							anaRegObj.setKvasys( lowAl + (highAl - lowAl) * random.nextDouble() );

							//Frequency
							anaRegObj.setHz( lowAl + (highAl - lowAl) * random.nextDouble()  );

							//Current
							anaRegObj.setAsys( lowAl + (highAl - lowAl) * random.nextDouble()  );

							//Power Factor
							anaRegObj.setPfsys( lowAl + (highAl - lowAl) * random.nextDouble() );

							anaRegObj.setAnalyzerId( analyzer.getId() );
							analyzersRegistries.add( anaRegObj );
						}
					}
					calendar.add(Calendar.DATE, 1);
				}

				clbDao.saveAnalyzerRegistries(analyzersRegistries);
				analyzersRegistries.stream().forEach( analyzerRegistry -> analyzer.addAnalyzerRegistryId( analyzerRegistry.getId() ) );
			}
		}
	}

	public Resource getDataAnalyzerXls() {
		return dataAnalyzerXls;
	}

	public void setDataAnalyzerXls(Resource dataAnalyzerXls) {
		this.dataAnalyzerXls = dataAnalyzerXls;
	}

}
