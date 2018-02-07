package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import clb.database.entities.UsersystemEntity;

@Repository
public interface UsersystemMongoRepository extends MongoRepository<UsersystemEntity, String>{

    @Query(value="{ 'token' : ?0 }", fields="{ 'token' : 1}")
    UsersystemEntity findUserbyToken(String token);
    
    @Query(value="{ 'username' : ?0 , 'password' : ?1 }", fields="{ 'username' : 1, 'password' : 1}")
    UsersystemEntity findUserbyUsernameAndPassword(String username, String password);
}
