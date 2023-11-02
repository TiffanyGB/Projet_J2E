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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.CategorieRepository;
import projetJEE.ProjetEE.Repersitory.ReserverRepository;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class VoyagesController {

    @Autowired
    private VoyageRepository voyageRepository;
    
    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private ReserverRepository reserverRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /****************ADMIN****************/
    @GetMapping("/voyages")
    public String afficherListeVoyages(HttpServletRequest request, Model model) {
	    extractTokenInfo(request, model);

	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }
	    
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
    public String modifierVoyageRedirection(HttpServletRequest request,@RequestParam Long voyageId,  Model model) {
	    extractTokenInfo(request, model);

	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }
        Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
        Iterable<Categorie> categories = categorieRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("voyage", voyage);
        return "admin/modifier_voyage_admin";
    }
    
    @PostMapping("/modifier-voyage")
    public String modifierVoyage(HttpServletRequest request,@ModelAttribute Voyage voyage, Model model) {
	    extractTokenInfo(request, model);

	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }
	    
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
	    extractTokenInfo(request, model);

	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }
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

        model.addAttribute("nbReserves", liste);
        model.addAttribute("voyagesReserves", voyagesReserves);
        
    	return "admin/liste_reservation";
    }
    
    /****************CLIENT****************/
    @GetMapping("/voyages-client")
    public String afficherListeVoyages_client(Model model,HttpServletRequest request) {
	    extractTokenInfo(request, model);

	    /*Mauvais profil, il faut etre client connecté ou non*/
	    if ((model.getAttribute("role") != null) && (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
	    
	    /*Recherche de tous les voyages*/
	    Iterable<Voyage> voyages = voyageRepository.findAll();
        Map<Long, Boolean> voyagesWithReservationStatus = new HashMap<>();

	    if (!model.asMap().isEmpty()) {
		    /*Rechercher du client*/

		    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
	    	Iterator<Utilisateur> iterator = tmp.iterator();
		    Utilisateur user = iterator.next();

		    /*Déterminer les voyages déjà réservés par celui-ci*/

	        for (Voyage voyage : voyages) {
	            List<Reserver> reservations = reserverRepository.findByUtilisateurAndVoyage(user, voyage);
	            boolean hasReservation = !reservations.isEmpty();
	            voyagesWithReservationStatus.put(voyage.getVoyageId(), hasReservation);
	        } 
	        
	        model.addAttribute("voyagesWithReservationStatus", voyagesWithReservationStatus);

	    }else {
	        for (Voyage voyage : voyages) {
	            voyagesWithReservationStatus.put(voyage.getVoyageId(), false);
	        } 
	    }
	    
	    model.addAttribute("voyages", voyages);

		return "client/liste_voyages_client";
	}
    
    
    @PostMapping("/reservation/{voyageId}")
    public String ajouterPanier(HttpServletRequest request,  @PathVariable Long voyageId,Model model) {
    	
	    extractTokenInfo(request, model);
	    /*Mauvais profil, il faut etre client connecté*/
	    if ((model.getAttribute("role") == null) || (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
	    
	    
        Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
        Categorie categorie = voyage.getIdCategorie();
        String nomCategorie = categorie.getNomCategorie();
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("nomCategorie", nomCategorie);

        return "client/form_reservation";
	}
    
    /*Pour afficher les erreurs*/
    @GetMapping("/reservation/{voyageId}")
    public String ajouterPanier1(HttpServletRequest request,  @PathVariable Long voyageId,Model model) {
    	
	    extractTokenInfo(request, model);
	    /*Mauvais profil, il faut etre client connecté*/
	    if ((model.getAttribute("role") == null) || (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
	 
       
        Voyage voyage = voyageRepository.findById(voyageId).orElse(null);
        Categorie categorie = voyage.getIdCategorie();
        String nomCategorie = categorie.getNomCategorie();
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("nomCategorie", nomCategorie);
        
        String error = (String) request.getSession().getAttribute("error");
        request.getSession().removeAttribute("error");
        model.addAttribute("error", error);

        return "client/form_reservation";
	}

    @PostMapping("/réserver-infos")
    public String reserverVoyage(@RequestParam("nbPersonnes") int nbPersonnes, 
            @RequestParam("dateDebut") Date dateDebut, 
            @RequestParam("dateFin") Date dateFin, 
            @RequestParam("voyageId") Long voyageId, 
            Model model,
            HttpServletRequest request) {
    	
		extractTokenInfo(request, model);
	    /*Mauvais profil, il faut etre client connecté*/
	    if ((model.getAttribute("role") == null) || (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
	    
    	Voyage voyage = voyageRepository.findById(voyageId).orElse(null);

    	if(voyage.getNbPlaces() < nbPersonnes) {
            request.getSession().setAttribute("error", "Le nombre de places souhaitées dépasse le nombre de places disponibles.");
            return "redirect:/reservation/" + voyageId;
    	}
	 

	    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
    	Iterator<Utilisateur> iterator = tmp.iterator();
	    Utilisateur user = iterator.next();
	    
    	voyage.setNbPlaces(voyage.getNbPlaces() - nbPersonnes);
        Reserver reservation = new Reserver();
        reservation.setNbPersonnes(nbPersonnes);
        reservation.setDateDebut(dateDebut);
        reservation.setDateFin(dateFin);
        reservation.setVoyage(voyage);
        reservation.setUtilisateur(user);
        reservation.setPrixTotal(nbPersonnes * voyage.getPrix_unitaire());

        reserverRepository.save(reservation);
        
        model.addAttribute("voyage", voyage);
        model.addAttribute("user", user);
        model.addAttribute("reservation",reservation);

    	return "client/confirmation_reservation";
	}
    
    @GetMapping("/recherche")
    public String rechercherVoyagesPage(HttpServletRequest request, Model model) {
    	extractTokenInfo(request, model);
	    /*Mauvais profil, il faut etre client connecté ou non*/
	    if ((model.getAttribute("role") != null) && (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
    	return "client/rechercheVoyages";
    }
    
    @PostMapping("/resultats-recherche")
    public String resultatsRecherche(@RequestParam("recherche") String recherche,HttpServletRequest request, Model model) {
    	
        extractTokenInfo(request, model);
        
	    /*Mauvais profil, il faut etre client connecté ou non*/
	    if ((model.getAttribute("role") != null) && (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
	    
    	Iterable<Voyage> voyagesPays = voyageRepository.findByPaysContainingIgnoreCase(recherche);
    	Iterable<Voyage> voyagesVilles = voyageRepository.findByVilleContainingIgnoreCase(recherche);
        Set<Voyage> resultatsRecherche = new HashSet<>();
        
        voyagesPays.forEach(resultatsRecherche::add);
        voyagesVilles.forEach(resultatsRecherche::add);
        
	    /*Recherche de tous les voyages*/
        Map<Long, Boolean> voyagesWithReservationStatus = new HashMap<>();

	    if (!model.asMap().isEmpty()) {
		    /*Rechercher du client*/

		    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
	    	Iterator<Utilisateur> iterator = tmp.iterator();
		    Utilisateur user = iterator.next();

		    /*Déterminer les voyages déjà réservés par celui-ci*/

	        for (Voyage voyage : resultatsRecherche) {
	            List<Reserver> reservations = reserverRepository.findByUtilisateurAndVoyage(user, voyage);
	            boolean hasReservation = !reservations.isEmpty();
	            voyagesWithReservationStatus.put(voyage.getVoyageId(), hasReservation);
	        } 
	        
	        model.addAttribute("voyagesWithReservationStatus", voyagesWithReservationStatus);

	    }else {
	        for (Voyage voyage : resultatsRecherche) {
	            voyagesWithReservationStatus.put(voyage.getVoyageId(), false);
	        } 
	    }
        
        model.addAttribute("resultats",resultatsRecherche);
        

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
            String email = (String) claims.get("email");

            model.addAttribute("username", username);
            model.addAttribute("token", token);
            model.addAttribute("role", role);
            model.addAttribute("email", email);

        }
    }

}