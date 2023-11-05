package projetJEE.ProjetEE.Models;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
    
    @Lob
    @Column(name="imageCategorie", columnDefinition = "MEDIUMBLOB")
    private byte[] imageCategorie;
    
    @OneToMany(mappedBy = "idCategorie", cascade = CascadeType.ALL)
    private Set<Voyage> voyages;


	public byte[] getImageCategorie() {
		return imageCategorie;
	}

	public void setImageCategorie(byte[] imageCategorie) {
		this.imageCategorie = imageCategorie;
	}

	public Set<Voyage> getVoyages() {
		return voyages;
	}

	public void setVoyages(Set<Voyage> voyages) {
		this.voyages = voyages;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(imageCategorie);
		result = prime * result + Objects.hash(idCategorie, nomCategorie, voyages);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(idCategorie, other.idCategorie) && Arrays.equals(imageCategorie, other.imageCategorie)
				&& Objects.equals(nomCategorie, other.nomCategorie) && Objects.equals(voyages, other.voyages);
	}    
	
	
	
}
