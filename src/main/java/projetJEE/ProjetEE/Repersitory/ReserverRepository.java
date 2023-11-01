package projetJEE.ProjetEE.Repersitory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Models.Voyage;

@Repository
public interface ReserverRepository extends CrudRepository<Reserver, Long> {
    Iterable<Reserver> findAll();
    Iterable<Reserver> findByVoyageVoyageId(Long voyageId);
    List<Reserver> findByUtilisateurAndVoyage(Utilisateur utilisateur, Voyage voyage);
    List<Reserver> findByUtilisateur(Utilisateur utilisateur);


}
