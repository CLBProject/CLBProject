package clb.business;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.AnalyzerRegistryObject;
import clb.database.ClbDao;
import clb.database.entities.AnalyzerRegistryEntity;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClbDao<AnalyzerRegistryEntity> clbDaoAnalyzer;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value(value = "classpath:documents/fileAnalyzerRegistries.xlsx")
    private Resource dataAnalyzerXls;

    public void init(){
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {

                try(ServerSocket s = new ServerSocket(1234)){
                    while(true){
                        Socket clientSocket = s.accept();
                        try(Scanner in = new Scanner(clientSocket.getInputStream())){
                            JSONObject jsonObj = new JSONObject(in.nextLine());
                            System.out.println(jsonObj.toString());
                        }
                        catch(JSONException jsonex){
                            jsonex.printStackTrace();
                        }
                    }
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void destroy(){

    }

    @Override
    public List<AnalyzerRegistryObject> getNewValuesToUpdate(Date sinceDate) {

        return clbDaoAnalyzer.getOnlyLatestCurrentAnalyzerRegistryData(sinceDate).stream()
                .map(AnalyzerRegistryObject::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void fillDatabaseDataWithMoreThenOneYears() {
        int numberOfYears = 2;
        int startingYear = 2017;
        int lowAl = 200;
        int highAl = 450;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set( startingYear, 0, 1);

        System.out.println( "Starting persist Data.." );
        
        Random random = new Random();
        
        for(int i = 0 ;i<numberOfYears; i++){
        	int yearDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            for(int l = 0; l < yearDays; l++ ){
                for(int j = 0; j < 24 ; j++){
                    for(int k = 0; k < 60; k++){
                        
                        AnalyzerRegistryObject anaRegObj = new AnalyzerRegistryObject();
                        anaRegObj.setCurrenttime( (j < 10 ? "0"+j : ""+j) + ":" + (k < 10 ? "0"+k : ""+k)+ ":00");
                        anaRegObj.setCurrentdate( calendar.getTime() ); 
                        
                        anaRegObj.setAl1(lowAl + (highAl - lowAl) * random.nextDouble());
                        anaRegObj.setAl2(lowAl + (highAl - lowAl) * random.nextDouble());
                        anaRegObj.setAl3(lowAl + (highAl - lowAl) * random.nextDouble());
                        
                        clbDaoAnalyzer.create( anaRegObj.toEntity() );
                        anaRegObj = null;
                    }
                    clbDaoAnalyzer.flush();
                }

                System.out.println( "Persisted Day: " + calendar.get( Calendar.DAY_OF_MONTH ) + ", from month " + calendar.get( Calendar.MONTH ) + ", from year " + calendar.get( Calendar.YEAR ));
                calendar.add(Calendar.DATE, 1);
            }
        }
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

            AnalyzerRegistryObject analyzerRegistryObject1 = new AnalyzerRegistryObject();
            analyzerRegistryObject1.setCurrentdate(row.getCell(0).getDateCellValue());

            calendar.setTime(row.getCell(1).getDateCellValue());   // assigns calendar to given date 

            if(calendar.get(Calendar.HOUR_OF_DAY) == currentHour){
                currentHourPassed = true;
                if(calendar.get(Calendar.MINUTE) == currentMinute){
                    currentMinutesPassed = true;
                }
            }

            analyzerRegistryObject1.setCurrenttime((calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY)+"") + ":" + 
                    (calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)+"") +":"+ 
                    (calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND) : calendar.get(Calendar.SECOND)));

            analyzerRegistryObject1.setAl1(row.getCell(2).getNumericCellValue());
            analyzerRegistryObject1.setAl2(row.getCell(3).getNumericCellValue());
            analyzerRegistryObject1.setAl3(row.getCell(4).getNumericCellValue());
            analyzerRegistryObject1.setHz(row.getCell(5).getNumericCellValue());
            analyzerRegistryObject1.setPfl1(row.getCell(6).getNumericCellValue());
            analyzerRegistryObject1.setPfl2(row.getCell(7).getNumericCellValue());
            analyzerRegistryObject1.setPfl3(row.getCell(8).getNumericCellValue());
            analyzerRegistryObject1.setPfsys(row.getCell(9).getNumericCellValue());
            //analyzerRegistryObject.setPhaseSequence(row.getCell(10).getNumericCellValue());
            analyzerRegistryObject1.setVl1l2(row.getCell(11).getNumericCellValue());
            analyzerRegistryObject1.setVl1n(row.getCell(12).getNumericCellValue());
            analyzerRegistryObject1.setVl2l3(row.getCell(13).getNumericCellValue());
            analyzerRegistryObject1.setVl2n(row.getCell(14).getNumericCellValue());
            analyzerRegistryObject1.setVl3l1(row.getCell(15).getNumericCellValue());
            analyzerRegistryObject1.setVl3n(row.getCell(16).getNumericCellValue());
            analyzerRegistryObject1.setVllsys(row.getCell(17).getNumericCellValue());
            analyzerRegistryObject1.setVlnsys(row.getCell(18).getNumericCellValue());
            analyzerRegistryObject1.setKval1(row.getCell(19).getNumericCellValue());
            analyzerRegistryObject1.setKval2(row.getCell(20).getNumericCellValue());
            analyzerRegistryObject1.setKval3(row.getCell(21).getNumericCellValue());
            analyzerRegistryObject1.setKvasys(row.getCell(22).getNumericCellValue());
            analyzerRegistryObject1.setKwh(row.getCell(23).getNumericCellValue());
            analyzerRegistryObject1.setKwl1(row.getCell(24).getNumericCellValue());
            analyzerRegistryObject1.setKwl2(row.getCell(25).getNumericCellValue());
            analyzerRegistryObject1.setKwl3(row.getCell(26).getNumericCellValue());
            analyzerRegistryObject1.setKwsys(row.getCell(27).getNumericCellValue());
            analyzerRegistryObject1.setKvarh(row.getCell(28).getNumericCellValue());
            analyzerRegistryObject1.setKvarl1(row.getCell(29).getNumericCellValue());
            analyzerRegistryObject1.setKvarl2(row.getCell(30).getNumericCellValue());
            analyzerRegistryObject1.setKvarl3(row.getCell(31).getNumericCellValue());
            analyzerRegistryObject1.setKvarsys(row.getCell(32).getNumericCellValue());

            if(currentHourPassed && currentMinutesPassed)
                analyzerRegistriesBeforeCurrentTime.add(analyzerRegistryObject1.toEntity());
            else analyzerRegistries.add(analyzerRegistryObject1.toEntity());
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

    public List<AnalyzerRegistryObject> getDataByDay(Date day) {
    	return new ArrayList<AnalyzerRegistryObject>(clbDaoAnalyzer.getAnalyzerRegistriesByDay(day).stream()
        .collect(Collectors.toMap(AnalyzerRegistryEntity::getCurrenttime,AnalyzerRegistryObject::new)).values());
    }

	@Override
	public Object[] getDataByYear(Integer year) {
		return clbDaoAnalyzer.getYearMonthAverages(year);
	}
    
	@Override
	public List<Integer> getRegistryYears() {
		return clbDaoAnalyzer.getRegistryYears();
	}

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }


}
