package clb.database.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import clb.database.entities.DivisionEntity;

@Repository
public interface DivisionRepository extends MongoRepository<DivisionEntity, String>{

}
