package projetJEE.ProjetEE.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.CategorieRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private VoyageRepository voyageRepository;

    /****************ADMIN****************/
   
    @GetMapping("/listeCategories")
    public String afficherCategories(Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
		return "admin/liste_catégories_admin";
	}
    
    @GetMapping("/a")
    public String a(Model model) {
		return "a";
	}
    
    @PostMapping("/ajouter-categorie")
    public String ajouterCategorie(@ModelAttribute Categorie categorie) {
    	categorieRepository.save(categorie); 
    	return "redirect:/listeCategories";
	}
    
    
    @PostMapping("/supprimer-categorie")
    public String supprimerVoyage(@RequestParam Long idCategorie) {
        categorieRepository.deleteById(idCategorie);
        return "redirect:/listeCategories";
    }
    
    /****************CLIENT****************/
    @GetMapping("/categories-client")
    public String afficherCategorieClient(Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
		return "client/liste_categories_client";
	}
    
    @GetMapping("/categorie-voyages/{id}")
    public String categorieDetails(@PathVariable Long id, Model model) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);

        List<Voyage> voyagesDeLaCategorie = voyageRepository.findByIdCategorie(categorie);
        model.addAttribute("voyages", voyagesDeLaCategorie);
        model.addAttribute("categorie", categorie);
    
        return "client/infos_categorie";
    }

}