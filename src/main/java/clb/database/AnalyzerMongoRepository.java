package clb.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.AnalyzerEntity;

@Repository
public interface AnalyzerMongoRepository extends MongoRepository<AnalyzerEntity, String>{

}
