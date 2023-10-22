package projetJEE.ProjetEE.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Voyage")
public class Voyage {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voyageId")
    private Long voyageId;
    
    @Column(name="imageVoyage", unique=false, nullable = true)
    private String imageVoyage;

	@Column(name="ville", unique=false, nullable = false, length = 50)
    private String ville;
    
    @Column(name="pays", unique=false, nullable = true, length = 50)
    private String pays;
    
    @Column(name="description", unique=false, nullable = false, length = 400)
    private String description; 
    
    @Column(name="prix_unitaire", unique=false,nullable = false, length = 100)
    private int prix_unitaire;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "idCategorie", referencedColumnName = "idCategorie")
    private Categorie idCategorie;

	public Long getVoyageId() {
		return voyageId;
	}

	public void setVoyageId(Long id) {
		this.voyageId = id;
	}
	
    public String getImageVoyage() {
		return imageVoyage;
	}

	public void setImageVoyage(String imageVoyage) {
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

	@Override
	public String toString() {
		return "Voyage [voyageId=" + voyageId + ", imageVoyage=" + imageVoyage + ", ville=" + ville + ", pays=" + pays
				+ ", description=" + description + ", prix_unitaire=" + prix_unitaire + ", idCategorie=" + idCategorie
				+ "]";
	}


    
}
