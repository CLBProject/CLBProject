package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import clb.database.entities.AnalyzerEntity;

@Repository
public interface AnalyzerMongoRepository extends MongoRepository<AnalyzerEntity, String>{

    @Query(value="{ 'codeName' : ?0 }")
    AnalyzerEntity findAnalyzerByCodename(String codename);
}
