package clb.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import clb.database.entities.AnalyzerEntity;
import clb.database.entities.AnalyzerRegistryEntity;
import clb.database.entities.BuildingEntity;
import clb.database.entities.DataLoggerEntity;
import clb.database.entities.UsersystemEntity;


public class ClbDaoImpl<T extends Serializable> implements ClbDao<T>, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    @PersistenceContext(unitName = "clbDatabase")
    protected EntityManager entityManager;

    public void create( T entity ){
        entityManager.persist( entity );
    }

    public T update( T entity ){
        return entityManager.merge( entity );
    }

    public void delete( T entity ){
        entityManager.remove( entity );
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager( EntityManager entityManager ) {
        this.entityManager = entityManager;
    }

    @Override
    public void persistData(List<T> data) {
        data.stream().forEach(Entity -> entityManager.persist(Entity));
    }

    /**
     * @return All Analyzer Registries
     */
    @Override
    public List<AnalyzerRegistryEntity> getAllCurrentAnalyzerRegistryData() {
        return entityManager.createNamedQuery("AnalyzerRegistry.findAll",AnalyzerRegistryEntity.class).getResultList();
    }

    @Override
    public void flush() {
        entityManager.flush();
        entityManager.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnalyzerRegistryEntity> getAnalyzerRegistriesByDay(Date date) {
        Query q = entityManager.createNamedQuery("AnalyzerRegistry.findAllByDay",AnalyzerRegistryEntity.class);
        q.setParameter("currentdate", date);

        List<AnalyzerRegistryEntity> resultList = q.getResultList();

        return resultList == null ? new ArrayList<AnalyzerRegistryEntity>() : resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnalyzerRegistryEntity> getAnalyzerRegistriesByDayAndHour(Date date, String hour) {
        Query q = entityManager.createNamedQuery("AnalyzerRegistry.findAllByDayHour",AnalyzerRegistryEntity.class);
        q.setParameter("currentdate", date);
        q.setParameter("currenthour", hour);

        List<AnalyzerRegistryEntity> resultList = q.getResultList();

        return resultList == null ? new ArrayList<AnalyzerRegistryEntity>() : resultList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getRegistryYears() {
        Query q = entityManager.createNativeQuery("select year(currentDate) from ANALYZER_REGISTRY group by year(currentDate)");

        List<Integer> resultList = q.getResultList();

        return resultList == null ? new ArrayList<Integer>() : resultList;
    }

    @Override
    public Collection<?> getYearMonthAverages(Integer year){
        Query q = entityManager.createNativeQuery("select avg(al1), avg(al2), avg(al3), year(currentDate), month(currentDate) "
                + "from analyzer_registry "
                + "where year(currentDate) = ?1 "
                + "group by year(currentDate), month(currentdate)");		
        q.setParameter(1, year);

        return q.getResultList();
    }

    @Override
    public Collection<?> getYearMonthDaysAverages( Integer yearSelected, Integer monthSelected ) {
        Query q = entityManager.createNativeQuery("select avg(al1), avg(al2), avg(al3), year(currentdate), month(currentdate),day(currentdate) "
                + "from clb.analyzer_registry "
                + "where year(currentdate) = ?1 and month(currentdate) = ?2 "
                + "group by year(currentdate),month(currentdate), day(currentdate)");       
        q.setParameter(1, yearSelected);
        q.setParameter(2, monthSelected);

        return q.getResultList();
    }

    @Override
    public void persistScriptBigData(){
    	UsersystemEntity userEntity = new UsersystemEntity();

        userEntity.setUserid( "nobreyeste@hotmail.com" );
        userEntity.setName( "Carlos Nobre" );
        userEntity.setAddress( "No address at this point" );
        userEntity.setUsername( "cnobre" );
        userEntity.setPassword( "123" );

        UsersystemEntity userEntity2 = new UsersystemEntity();

        userEntity2.setUserid( "brunocatela@hotmail.com" );
        userEntity2.setName( "Bruno Catela" );
        userEntity2.setAddress( "No address at this point" );
        userEntity2.setUsername( "bcatela" );
        userEntity2.setPassword( "123" );

        UsersystemEntity userEntity3 = new UsersystemEntity();

        userEntity3.setUserid( "luissantos@hotmail.com" );
        userEntity3.setName( "Luis Santos" );
        userEntity3.setAddress( "No address at this point" );
        userEntity3.setUsername( "lsantos" );
        userEntity3.setPassword( "123" );


        BuildingEntity buildingEntity = new BuildingEntity();
        buildingEntity.setName( "Amanjena Hotel" );
        buildingEntity.setUsersystem(userEntity);

        BuildingEntity buildingEntity2 = new BuildingEntity();
        buildingEntity2.setName( "AquaMirage Hotel" );
        buildingEntity.setUsersystem(userEntity);

        BuildingEntity buildingEntity3 = new BuildingEntity();
        buildingEntity3.setName( "Ritz" );
        buildingEntity.setUsersystem(userEntity2);

        BuildingEntity buildingEntity4 = new BuildingEntity();
        buildingEntity4.setName( "VASP" );
        buildingEntity.setUsersystem(userEntity3);
        
        int numberOfYears = 2;
        int startingYear = 2017;
        int lowAl = 200;
        int highAl = 450;

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set( startingYear, 0, 1);

        System.out.println( "Starting persist Data.." );

        Random random = new Random();
        
        List<AnalyzerEntity> analyzers = new ArrayList<AnalyzerEntity>();
        
        //Create Data Loggers
        for(int i=0 ;i< 100; i++){
            DataLoggerEntity dlObj = new DataLoggerEntity();
            dlObj.setName("Data Logger " + i);
            
            System.out.println("Starting Persist for data logger :"+ i);
            
            for(int j=0;j<100;j++){
                AnalyzerEntity analyzerEntity = new AnalyzerEntity();
                analyzerEntity.setName("Analyzer "+j);
                analyzerEntity.setDataLogger(dlObj);
                entityManager.persist(analyzerEntity);
                analyzers.add(analyzerEntity);
            }
            
            entityManager.flush();

            if(i < 25){
            	dlObj.setBuilding(buildingEntity);
            }
            else if(i >=25 && i < 50){
            	dlObj.setBuilding(buildingEntity2);
            }
            else if(i >=50 && i < 75){
            	dlObj.setBuilding(buildingEntity3);
            }
            else{
            	dlObj.setBuilding(buildingEntity4);
            }
        }
        
        int m = 0;
        
        for(int a = 0 ;a<numberOfYears; a++){
            int yearDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            for(int l = 0; l < yearDays; l++ ){
                for(int b = 0; b < 24 ; b++){
                    for(int k = 0; k < 60; k++){

                        AnalyzerRegistryEntity anaRegObj = new AnalyzerRegistryEntity();
                        anaRegObj.setCurrenttime( (b < 10 ? "0"+b : ""+b) + ":" + (k < 10 ? "0"+k : ""+k)+ ":00");
                        anaRegObj.setCurrentdate( calendar.getTime() ); 

                        anaRegObj.setAl1(lowAl + (highAl - lowAl) * random.nextDouble());
                        anaRegObj.setAl2(lowAl + (highAl - lowAl) * random.nextDouble());
                        anaRegObj.setAl3(lowAl + (highAl - lowAl) * random.nextDouble());
                        
                        anaRegObj.setAnalyzer(analyzers.get(m));
                        
                        m++;
                        
                        m = m == analyzers.size() ? 0 : m;
                        
                        entityManager.persist(anaRegObj);
                        anaRegObj = null;
                    }
                    entityManager.flush();
                }

                System.out.println( "Persisted Day: " + calendar.get( Calendar.DAY_OF_MONTH ) + ", from month " + calendar.get( Calendar.MONTH ) + ", from year " + calendar.get( Calendar.YEAR ));
                calendar.add(Calendar.DATE, 1);
            }
        	
        }
    }
}
