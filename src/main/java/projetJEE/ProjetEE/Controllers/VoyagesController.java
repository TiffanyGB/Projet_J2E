package projetJEE.ProjetEE.Controllers;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public String afficherListeVoyages(HttpServletRequest request, Model model) {
		((Model) model).addAttribute("voyages", voyageRepository.findAll());
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
	    extractTokenInfo(request, model);

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
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("voyage", voyage);
        return "admin/modifier_voyage_admin";
    }
    
    @PostMapping("/modifier-voyage")
    public String modifierVoyage(@ModelAttribute Voyage voyage) {
    	
        Voyage voyageExistant = voyageRepository.findById(voyage.getVoyageId()).orElse(null);

        if (voyageExistant != null) {
            voyageExistant.setVille(voyage.getVille());
            voyageExistant.setDescription(voyage.getDescription());
            voyageExistant.setPays(voyage.getPays());
            voyageExistant.setNbPlaces(voyage.getNbPlaces());
            voyageExistant.setImageVoyage(voyage.getImageVoyage());
            voyageExistant.setIdCategorie(voyage.getIdCategorie());
            voyageExistant.setPrix_unitaire(voyage.getPrix_unitaire());
            voyageRepository.save(voyageExistant);
        }
        return "redirect:/voyages";
    }   
    
    @GetMapping("reservations-liste")
    public String listeReservations(HttpServletRequest request,Model model) {
    	Iterable<Reserver> reservations = reserverRepository.findAll();
    	
    	/*Ajout des voyages qui ont au moins une réservation*/
    	Set<Voyage> voyagesReserves = new HashSet<>();
        for (Reserver reservation : reservations) {
            voyagesReserves.add(reservation.getVoyage());
        } 	
        
        /*Calcul du nombre de places réservés dans chaque voyage*/
        Map<Long, Integer> liste = new HashMap<>();
        Iterator<Voyage> it = voyagesReserves.iterator();
        
        while(it.hasNext()) {
        	Long idCourant = it.next().getVoyageId();
        	liste.put(idCourant, 0);
        	Iterable<Reserver> reservationsVoyages = reserverRepository.findByVoyageVoyageId(idCourant);
        	Iterator<Reserver> itR = reservationsVoyages.iterator();
        	while(itR.hasNext()) {
            	liste.put(idCourant, liste.get(idCourant) + itR.next().getNbPersonnes());
        	}
        }
	    extractTokenInfo(request, model);

        
        model.addAttribute("nbReserves", liste);
        model.addAttribute("voyagesReserves", voyagesReserves);
        
    	return "admin/liste_reservation";
    }
    
    /****************CLIENT****************/
    @GetMapping("/voyages-client")
    public String afficherListeVoyages_client(Model model,HttpServletRequest request) {
		((Model) model).addAttribute("voyages", voyageRepository.findAll());
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
	    extractTokenInfo(request, model);

		return "client/liste_voyages_client";
	}
    
    @PostMapping("/reservation")
    public String ajouterPanier(HttpServletRequest request,@RequestParam Long voyageId,  Model model) {
        Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
        Categorie categorie = voyage.getIdCategorie();
        String nomCategorie = categorie.getNomCategorie();
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("nomCategorie", nomCategorie);
		extractTokenInfo(request, model);

        return "client/form_reservation";
	}

    @PostMapping("/réserver-infos")
    public String reserverVoyage(@RequestParam("nbPersonnes") int nbPersonnes, 
            @RequestParam("dateDebut") Date dateDebut, 
            @RequestParam("dateFin") Date dateFin, 
            @RequestParam("voyageId") Long voyageId, 
            Model model,
            HttpServletRequest request) {
    	
    	Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
    	voyage.setNbPlaces(voyage.getNbPlaces() - nbPersonnes);
        Reserver reservation = new Reserver();
        reservation.setNbPersonnes(nbPersonnes);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setVoyage(voyage);
        reservation.setPrixTotal(nbPersonnes * voyage.getPrix_unitaire());

        reserverRepository.save(reservation);
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("reservation",reservation);
		extractTokenInfo(request, model);

    	return "client/confirmation_reservation";
	}
    
    @GetMapping("/recherche")
    public String rechercherVoyagesPage(HttpServletRequest request, Model model) {
    	extractTokenInfo(request, model);
    	return "client/rechercheVoyages";
    }
    
    @PostMapping("/resultats-recherche")
    public String resultatsRecherche(@RequestParam("recherche") String recherche,HttpServletRequest request, Model model) {
    	Iterable<Voyage> voyagesPays = voyageRepository.findByPaysContainingIgnoreCase(recherche);
    	Iterable<Voyage> voyagesVilles = voyageRepository.findByVilleContainingIgnoreCase(recherche);
        Set<Voyage> resultatsRecherche = new HashSet<>();
        voyagesPays.forEach(resultatsRecherche::add);
        voyagesVilles.forEach(resultatsRecherche::add);
        model.addAttribute("resultats",resultatsRecherche);
        extractTokenInfo(request, model);
    	return "client/rechercheResultats";
    }
    
    private void extractTokenInfo(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            String secretKey = "abcdef";

            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            Boolean role = (Boolean) claims.get("role");

            model.addAttribute("username", username);
            model.addAttribute("token", token);
            model.addAttribute("role", role);
        }
    }

}