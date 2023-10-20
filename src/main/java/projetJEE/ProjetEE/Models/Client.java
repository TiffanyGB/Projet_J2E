package projetJEE.ProjetEE.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Client")
public class Client {
    @Id
    @ManyToOne
    @JoinColumn(name = "idUtilisateur")
    private Utilisateur idClient;    

    
}
