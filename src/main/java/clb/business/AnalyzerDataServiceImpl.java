package clb.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clb.business.objects.AnalyzerObject;
import clb.business.objects.AnalyzerRegistryAverageObject;
import clb.business.objects.AnalyzerRegistryObject;
import clb.business.objects.BuildingObject;
import clb.business.objects.ClbObject;
import clb.business.objects.DataLoggerObject;
import clb.business.objects.UsersystemObject;
import clb.database.ClbDao;
import clb.database.entities.UsersystemEntity;

@Service
public class AnalyzerDataServiceImpl implements AnalyzerDataService, Serializable{

    /** 
     * 
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value(value = "classpath:documents")
    private Resource dataAnalyzerXls;

    @Autowired
    private ClbDao clbDao;
    
    @Autowired 
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void persistScriptBigData() throws IOException{
        UsersystemObject userObject = new UsersystemObject();

        userObject.setUserid( "nobreyeste@hotmail.com" );
        userObject.setName( "Carlos Nobre" );
        userObject.setAddress( "No address at this point" );
        userObject.setUsername( "cnobre" );
        userObject.setPassword( "123" );

        UsersystemObject userObject2 = new UsersystemObject();

        userObject2.setUserid( "brunocatela@hotmail.com" );
        userObject2.setName( "Bruno Catela" );
        userObject2.setAddress( "No address at this point" );
        userObject2.setUsername( "bcatela" );
        userObject2.setPassword( "123" );

        UsersystemObject userObject3 = new UsersystemObject();

        userObject3.setUserid( "luissantos@hotmail.com" );
        userObject3.setName( "Luis Santos" );
        userObject3.setAddress( "No address at this point" );
        userObject3.setUsername( "lsantos" );
        userObject3.setPassword( "123" );

        List<UsersystemObject> users = new ArrayList<UsersystemObject>();

        users.add( userObject );
        users.add( userObject2 );
        users.add( userObject3 );

        int usersIndex = 0;

        for(File file: dataAnalyzerXls.getFile().listFiles()){
            updateAnalyzerRegistriesForAnalyzer(file,users.get( usersIndex ));
            usersIndex = usersIndex +1 == users.size() ? 0 : usersIndex+1;
        }

        clbDao.saveUsers(users);
    }

    @Override
    @Transactional
    public void saveObject(ClbObject clbObject) {
        if(clbObject instanceof UsersystemObject) {
            clbDao.saveUsersystem((UsersystemObject)clbObject);
        }
    }

    @Override
    @Transactional
    public UsersystemObject userCanLogin( String userName, String password ) {
        UsersystemEntity userEntity = clbDao.userCanLogin(userName, password);

        return userEntity != null ? new UsersystemObject(userEntity) : null;
    }

    @Override
    public UsersystemObject getUsersystemByToken( String token ) {
        return new UsersystemObject(clbDao.findUserByToken(token));
    }


    @Override
    public void publishEvent( UsersystemObject object, Locale requestLocale, String requestContextPath ) {
        eventPublisher.publishEvent(new AnalyzerDataServiceRegisterOnCompleteEvent(object, requestLocale, requestContextPath));
    }

    private boolean isAverageDate(Date currentDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        Calendar calendarToday = GregorianCalendar.getInstance();
        calendar.setTime(new Date());

        return calendarToday.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && 
                calendarToday.get(Calendar.MONTH) == calendar.get(Calendar.MONTH);
    }

    private void updateAnalyzerRegistriesForAnalyzer(File file, UsersystemObject userObject) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));

        BuildingObject building = new BuildingObject();
        building.setName(file.getName().split("\\.")[0]);
        building.setBuildingusername(file.getName().split("\\.")[0]);
        userObject.addBuilding(building);

        for(int j = 0; j<workbook.getNumberOfSheets();j++){

            DataLoggerObject dl = new DataLoggerObject();
            dl.setName( "Data Logger "+j );
            dl.setFtpAddress( "ftp://noftp" );
            building.addDataLogger(dl);


            AnalyzerObject ana = new AnalyzerObject();
            ana.setName( "Analyzer " + j);
            dl.addAnalyzer(ana);

            XSSFSheet worksheet = workbook.getSheetAt( j );

            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance

            Set<Long> dataToExclueOnDummy = new HashSet<Long>();

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

                analyzerRegistryObject.setCurrenttime( new Timestamp(calendar.getTime().getTime()) );
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

                clbDao.saveAnalyzerRegistry(analyzerRegistryObject);

                ana.addAnalyzerRegistry(analyzerRegistryObject);
            }

            persistDummyAnalyzerRegistries( ana, dataToExclueOnDummy);

            clbDao.saveAnalyzer(ana);
            clbDao.saveDataLogger(dl);
        }

        clbDao.saveBuilding(building);
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
                for(int m=0; m < calendar.getActualMaximum(Calendar.DAY_OF_MONTH);m++) {

                    double al1DailyAverage = 0;
                    double al2DailyAverage = 0;
                    double al3DailyAverage = 0;

                    for(int b = 0; b < 24 ; b++){
                        for(int k = 0; k < 60; k+=5){

                            Calendar currentTimeCalendar = Calendar.getInstance();
                            currentTimeCalendar.set(Calendar.YEAR, a+startingYear);
                            currentTimeCalendar.set(Calendar.MONTH, l);
                            currentTimeCalendar.set(Calendar.DAY_OF_MONTH, m);
                            currentTimeCalendar.set(Calendar.HOUR_OF_DAY, b);
                            currentTimeCalendar.set(Calendar.MINUTE, k);
                            currentTimeCalendar.set(Calendar.SECOND, 0);

                            Date time = currentTimeCalendar.getTime();

                            if(dataToExclude.contains( time.getTime())){
                                continue;
                            }

                            //Test if reg all time or only 5 mins
                            if(isAverageDate(time)) {

                                AnalyzerRegistryObject anaRegObj = new AnalyzerRegistryObject();

                                anaRegObj.setCurrenttime( new Timestamp(time.getTime()) );

                                anaRegObj.setAl1(lowAl + (highAl - lowAl) * random.nextDouble());
                                anaRegObj.setAl2(lowAl + (highAl - lowAl) * random.nextDouble());
                                anaRegObj.setAl3(lowAl + (highAl - lowAl) * random.nextDouble());

                                al1DailyAverage +=anaRegObj.getAl1();
                                al2DailyAverage +=anaRegObj.getAl2();
                                al3DailyAverage +=anaRegObj.getAl3();

                                clbDao.saveAnalyzerRegistry(anaRegObj);
                                analyzer.addAnalyzerRegistry(anaRegObj);
                            }

                            else {
                                al1DailyAverage +=lowAl + (highAl - lowAl) * random.nextDouble();
                                al2DailyAverage +=lowAl + (highAl - lowAl) * random.nextDouble();
                                al3DailyAverage +=lowAl + (highAl - lowAl) * random.nextDouble();
                            }
                        }
                    }

                    AnalyzerRegistryAverageObject anaRegAverageObj = new AnalyzerRegistryAverageObject();

                    anaRegAverageObj.setCurrenttime( new Timestamp(calendar.getTime().getTime()) );

                    anaRegAverageObj.setAl1Average(al1DailyAverage/ (24*60));
                    anaRegAverageObj.setAl2Average(al2DailyAverage/ (24*60));
                    anaRegAverageObj.setAl3Average(al3DailyAverage/ (24*60));

                    clbDao.saveAnalyzerRegistryAverage(anaRegAverageObj);
                    analyzer.addAnalyzerRegistryAverage(anaRegAverageObj);

                    calendar.add(Calendar.DATE, 1);
                }

            }
            System.out.println("Persisted Registries from year: " + (a+startingYear) + ", for analyzer: " + analyzer.getId());
        }

    }

    public void destroy(){

    }


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

    public Resource getDataAnalyzerXls() {
        return dataAnalyzerXls;
    }

    public void setDataAnalyzerXls(Resource dataAnalyzerXls) {
        this.dataAnalyzerXls = dataAnalyzerXls;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public ClbDao getClbDao() {
        return clbDao;
    }

    public void setClbDao(ClbDao clbDao) {
        this.clbDao = clbDao;
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher( ApplicationEventPublisher eventPublisher ) {
        this.eventPublisher = eventPublisher;
    }
}
