package projetJEE.ProjetEE.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Administrateur")
public class Administrateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    
}
