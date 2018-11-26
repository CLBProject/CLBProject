package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
<<<<<<< HEAD
=======
import org.springframework.data.mongodb.repository.Query;
>>>>>>> 1f6fa284475686915235e657293699f5e89dcc6a
import org.springframework.stereotype.Repository;

import clb.database.entities.UsersystemEntity;

@Repository
public interface UsersystemMongoRepository extends MongoRepository<UsersystemEntity, String>{

<<<<<<< HEAD
=======
    @Query(value="{ 'username' : ?0 }")
    UsersystemEntity findUserbyUsername(String username);
    
    @Query(value="{ 'token' : ?0 }")
    UsersystemEntity findUserbyToken(String token);
>>>>>>> 1f6fa284475686915235e657293699f5e89dcc6a
}
