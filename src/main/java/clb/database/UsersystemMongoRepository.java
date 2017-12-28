package clb.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.UsersystemEntity;

@Repository
public interface UsersystemMongoRepository extends MongoRepository<UsersystemEntity, String>{

}
