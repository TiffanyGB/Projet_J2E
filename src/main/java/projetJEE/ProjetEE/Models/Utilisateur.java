package projetJEE.ProjetEE.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name = "numeroTelephone", nullable = false)
    private String numeroTelephone;
	
    @Column(name="mdp", unique=false,nullable = false, length = 50)
    private String mdp;
    
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
	public String getNumeroTelephone() {
		return numeroTelephone;
	}
	public void setNumeroTelephone(String numeroTelephone) {
		this.numeroTelephone = numeroTelephone;
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
