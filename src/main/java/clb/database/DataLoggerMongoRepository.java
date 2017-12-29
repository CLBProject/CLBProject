package clb.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.DataLoggerEntity;

@Repository
public interface DataLoggerMongoRepository extends MongoRepository<DataLoggerEntity, String>{

}
