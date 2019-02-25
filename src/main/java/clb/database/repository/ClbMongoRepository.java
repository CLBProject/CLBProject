package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import clb.database.entities.ClbEntity;
import clb.database.entities.UsersystemEntity;

@Repository
public interface ClbMongoRepository extends MongoRepository<ClbEntity, String>{
    
    @Query(value="{ 'token' : ?0 }")
    UsersystemEntity findUserbyToken(String token);
}
