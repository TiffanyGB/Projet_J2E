package projetJEE.ProjetEE.Models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUtilisateur;
    
    @Column(name="nom", unique=false, nullable = false, length = 50)
    private String nom;
    
    @Column(name="admin", unique=false, nullable = false, length = 50)
    private Boolean admin;
    
    @Column(name="prenom", unique=false, nullable = false, length = 50)
    private String prenom;
    
    @Column(name="email", unique=true,nullable = false, length = 50)
    private String email;
	
    @Column(name="mdp", unique=false,nullable = false, length = 400)
    private String mdp;
    
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private Set<Reserver> reservations = new HashSet<>();
    
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(Long id) {
		this.idUtilisateur = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
    
}
