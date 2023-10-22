package projetJEE.ProjetEE.Repersitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import projetJEE.ProjetEE.Models.Reserver;

@Repository
public interface ReserverRepository extends CrudRepository<Reserver, Long> {
    Iterable<Reserver> findAll();

    

}
