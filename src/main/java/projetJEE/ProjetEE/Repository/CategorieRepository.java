package projetJEE.ProjetEE.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Categorie;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {
    Iterable<Categorie> findAll();


}
