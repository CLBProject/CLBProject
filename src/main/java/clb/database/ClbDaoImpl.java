package clb.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import clb.database.entities.AnalyzerRegistryEntity;


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
        data.stream().forEach(object -> entityManager.persist(object));
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

}
