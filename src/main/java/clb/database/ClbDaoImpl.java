package clb.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
	
	//TODO returns all the recent data
	public List<AnalyzerRegistryEntity> getOnlyLatestCurrentAnalyzerRegistryData(Date sinceDate){
		return null;
	}
    
}
