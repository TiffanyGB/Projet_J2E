package projetJEE.ProjetEE.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Administrateur")
public class Administrateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdministrateur")
    private Long idAdministrateur;
   
    @Column(name = "boutique")
    private String boutique;
    
    @OneToOne
    @JoinColumn(name = "idUtilisateur", referencedColumnName = "idUtilisateur")
    private Utilisateur idUtilisateur;

	public Long getIdAdministrateur() {
		return idAdministrateur;
	}

	public void setIdAdministrateur(Long idAdministrateur) {
		this.idAdministrateur = idAdministrateur;
	}

	public String getBoutique() {
		return boutique;
	}

	public Utilisateur getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(Utilisateur idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public void setBoutique(String boutique) {
		this.boutique = boutique;
	}




    
}
