package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import clb.database.entities.UsersystemEntity;

@Repository
public interface UsersystemMongoRepository extends MongoRepository<UsersystemEntity, String>{

    @Query(value="{ 'username' : ?0 }")
    UsersystemEntity findUserbyUsername(String username);
    
    @Query(value="{ 'token' : ?0 }")
    UsersystemEntity findUserbyToken(String token);
}
