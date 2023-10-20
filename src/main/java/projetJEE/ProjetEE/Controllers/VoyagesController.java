package projetJEE.ProjetEE.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class VoyagesController {

    @Autowired
    private VoyageRepository voyageRepository;

    @GetMapping("/voyages")
    public String afficherListeVoyages(Model model) {
		((Model) model).addAttribute("voyages", voyageRepository.findAll());
		return "produits";
	}
    
    @PostMapping("/ajouter-voyage")
    public String ajouterVoyage(@ModelAttribute Voyage voyage) {
    	voyageRepository.save(voyage);
    	return "redirect:/voyages";
	}
    
    @PostMapping("/supprimer-voyage")
    public String supprimerVoyage(@RequestParam Long voyageId) {
        voyageRepository.deleteById(voyageId);
        return "redirect:/voyages"; // Redirigez l'utilisateur vers la liste mise à jour
    }

}