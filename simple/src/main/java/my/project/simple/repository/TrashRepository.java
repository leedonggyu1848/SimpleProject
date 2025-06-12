package my.project.simple.repository;


import java.util.List;
import my.project.simple.domain.Trash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("trashRepository")
public interface TrashRepository extends JpaRepository<Trash, Long> {
	List<Trash> findByContent(String content);
}
