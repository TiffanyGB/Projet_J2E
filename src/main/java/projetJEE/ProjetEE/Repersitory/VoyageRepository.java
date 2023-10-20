package projetJEE.ProjetEE.Repersitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Voyage;

@Repository
public interface VoyageRepository extends CrudRepository<Voyage, Long> {
	
    Iterable<Voyage> findAll();
    

}
