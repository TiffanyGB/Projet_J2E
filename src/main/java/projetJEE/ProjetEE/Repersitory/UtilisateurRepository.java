package projetJEE.ProjetEE.Repersitory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Utilisateur;

@Repository
public interface UtilisateurRepository extends CrudRepository<Utilisateur, Long> {
    List<Utilisateur> findByEmailAndMdp(String email, String mdp);
    Iterable<Utilisateur> findByEmail(String email);
}
