package projetJEE.ProjetEE.Models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Categorie")
public class Categorie {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategorie")
    private Long idCategorie;
    
    @Column(name="nomCategorie", unique=false, nullable = false)
    private String nomCategorie;
    
    @Column(name="imageCategorie", unique=false, nullable = false)
    private String imageCategorie;
    
    @OneToMany(mappedBy = "idCategorie", cascade = CascadeType.ALL)
    private Set<Voyage> voyages;

	public String getImageCategorie() {
		return imageCategorie;
	}

	public void setImageCategorie(String imageCategorie) {
		this.imageCategorie = imageCategorie;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	@Override
	public String toString() {
		return "Categorie [idCategorie=" + idCategorie + ", nomCategorie=" + nomCategorie + ", imageCategorie="
				+ imageCategorie + "]";
	}    
	
	
}
