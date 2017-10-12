package clb.database;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class ClbDaoImpl<T extends Serializable> implements ClbDao<T>{

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

    
}
