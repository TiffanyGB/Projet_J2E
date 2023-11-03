package projetJEE.ProjetEE.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.CategorieRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class ImageController {

    @Autowired
    private VoyageRepository voyageRepository;
    
    @Autowired
    private CategorieRepository categorieRepository;

	@GetMapping("/displayImage/{id}")
	public ResponseEntity<byte[]> displayImage(@PathVariable Long id) {
	    java.util.Optional<Voyage> produit = voyageRepository.findById(id);
	    
	    if (produit.isPresent() && produit.get().getImageVoyage() != null) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) // Assurez-vous de définir le type de contenu approprié
	                .body(produit.get().getImageVoyage());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping("/displayImageCat/{id}")
	public ResponseEntity<byte[]> displayImageCat(@PathVariable Long id) {
	    java.util.Optional<Categorie> produit = categorieRepository.findById(id);
	    
	    if (produit.isPresent() && produit.get().getImageCategorie() != null) {
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) 
	                .body(produit.get().getImageCategorie());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

    
    


}