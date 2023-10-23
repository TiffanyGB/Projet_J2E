package projetJEE.ProjetEE.Controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.CategorieRepository;
import projetJEE.ProjetEE.Repersitory.ReserverRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class VoyagesController {

    @Autowired
    private VoyageRepository voyageRepository;
    
    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private ReserverRepository reserverRepository;

    /****************ADMIN****************/
    @GetMapping("/voyages")
    public String afficherListeVoyages(Model model) {
		((Model) model).addAttribute("voyages", voyageRepository.findAll());
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
		return "admin/liste_voyages_admin";
	}
    
    @PostMapping("/ajouter-voyage")
    public String ajouterVoyage(@ModelAttribute Voyage voyage) {
    	voyageRepository.save(voyage); 
    	return "redirect:/voyages";
	}
    
    @PostMapping("/supprimer-voyage")
    public String supprimerVoyage(@RequestParam Long voyageId) {
        voyageRepository.deleteById(voyageId);
        return "redirect:/voyages";
    }
    
    
    @PostMapping("/modifier-voyage-redirection")
    public String modifierVoyageRedirection(@RequestParam Long voyageId,  Model model) {
    	
        Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
        model.addAttribute("voyage", voyage);
        return "admin/modifier_voyage_admin";
    }
    
    @PostMapping("/modifier-voyage")
    public String modifierVoyage(@ModelAttribute Voyage voyage) {
    	
        Voyage voyageExistant = voyageRepository.findById(voyage.getVoyageId()).orElse(null);

        if (voyageExistant != null) {
            voyageExistant.setVille(voyage.getVille());
            voyageExistant.setDescription(voyage.getDescription());
            voyageExistant.setPrix_unitaire(voyage.getPrix_unitaire());
            voyageRepository.save(voyageExistant);
        } else {
        	System.out.println("else");
            return "redirect:/voyages";
        }
        return "redirect:/voyages";
    }   
    
    /****************CLIENT****************/
    @GetMapping("/voyages-client")
    public String afficherListeVoyages_client(Model model) {
		((Model) model).addAttribute("voyages", voyageRepository.findAll());
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
		return "client/liste_voyages_client";
	}
    
    @PostMapping("/reservation")
    public String ajouterPanier(@RequestParam Long voyageId,  Model model) {
        Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
        Categorie categorie = voyage.getIdCategorie();
        String nomCategorie = categorie.getNomCategorie();
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("nomCategorie", nomCategorie);
        
        return "client/form_reservation";
	}

    @PostMapping("/réserver-infos")
    public String reserverVoyage(@RequestParam("nbPersonnes") int nbPersonnes, 
            @RequestParam("dateDebut") Date dateDebut, 
            @RequestParam("dateFin") Date dateFin, 
            @RequestParam("voyageId") Long voyageId, 
            Model model) {
    	
    	Voyage voyage = voyageRepository.findById(voyageId).orElse(null);

        Reserver reservation = new Reserver();
        reservation.setNbPersonnes(nbPersonnes);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setVoyage(voyage);
        reservation.setPrixTotal(nbPersonnes * voyage.getPrix_unitaire());

        reserverRepository.save(reservation);
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("reservation",reservation);
        
        System.out.println(model);
    	return "client/confirmation_reservation";
	}
    
    @GetMapping("/recherche")
    public String rechercherVoyagesPage() {
    	return "client/rechercheVoyages";
    }
    
    @PostMapping("/resultats-recherche")
    public String resultatsRecherche(@RequestParam("recherche") String recherche, Model model) {
    	Iterable<Voyage> voyagesPays = voyageRepository.findByPaysContainingIgnoreCase(recherche);
    	Iterable<Voyage> voyagesVilles = voyageRepository.findByVilleContainingIgnoreCase(recherche);
        Set<Voyage> resultatsRecherche = new HashSet<>();
        voyagesPays.forEach(resultatsRecherche::add);
        voyagesVilles.forEach(resultatsRecherche::add);
        model.addAttribute("resultats",resultatsRecherche);

    	return "client/rechercheResultats";
    }

}