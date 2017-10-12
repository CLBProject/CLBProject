package clb.database;

import java.io.Serializable;

public interface ClbDao<T extends Serializable> {

    public void create( T entity );
    
     public T update( T entity );
     
     public void delete( T entity );
}
