package projetJEE.ProjetEE.Repersitory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Voyage;

@Repository
public interface VoyageRepository extends CrudRepository<Voyage, Long> {
	
    Iterable<Voyage> findAll();
    List<Voyage> findByIdCategorie(Categorie categorie);
    Iterable<Voyage> findByVilleContainingIgnoreCase(String ville);
    Iterable<Voyage> findByPaysContainingIgnoreCase(String pays);


}
