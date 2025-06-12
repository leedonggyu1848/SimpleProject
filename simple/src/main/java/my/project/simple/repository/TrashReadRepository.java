package my.project.simple.repository;

import my.project.simple.domain.Trash;
import my.project.simple.domain.TrashRead;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("trashReadRepository")
public interface TrashReadRepository extends MongoRepository<TrashRead, Long> {
}
