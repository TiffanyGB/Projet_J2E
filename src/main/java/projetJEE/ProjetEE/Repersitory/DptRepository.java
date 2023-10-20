package projetJEE.ProjetEE.Repersitory;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Department;

@Repository
public interface DptRepository extends CrudRepository<Department, Long> {
	
	Optional<Department> findByName(String name);
}
