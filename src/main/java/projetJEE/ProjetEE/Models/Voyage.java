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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Voyage")
public class Voyage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voyageId")
    private Long voyageId;
	
    @Lob
    @Column(name = "imageVoyage", columnDefinition = "MEDIUMBLOB")
    private byte[] imageVoyage;

	@Column(name="ville", unique=false, nullable = false, length = 50)
    private String ville;
    
    @Column(name="pays", unique=false, nullable = true, length = 50)
    private String pays;
    
    @Column(name="description", unique=false, nullable = false, length = 400)
    private String description; 
    
    @Column(name="prix_unitaire", unique=false,nullable = false, length = 100)
    private int prix_unitaire;
    
    @Column(name="nbPlaces", unique=false,nullable = false, length = 100)
    private int nbPlaces;


    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie idCategorie;
    
    
	public Long getVoyageId() {
		return voyageId;
	}

	public void setVoyageId(Long id) {
		this.voyageId = id;
	}
	
	public byte[] getImageVoyage() {
		return imageVoyage;
	}

	public void setImageVoyage(byte[] imageVoyage) {
		this.imageVoyage = imageVoyage;
	}
	
	
	public Categorie getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Categorie idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrix_unitaire() {
		return prix_unitaire;
	}

	public void setPrix_unitaire(int prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

    
}
