package projetJEE.ProjetEE;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DptRepository extends CrudRepository<Department, Long> {
	
	Optional<Department> findByName(String name);
}
