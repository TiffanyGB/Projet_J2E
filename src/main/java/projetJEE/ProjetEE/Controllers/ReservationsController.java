package projetJEE.ProjetEE.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.ReserverRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class ReservationsController {

    @Autowired
    private VoyageRepository voyageRepository;
    
    
    @Autowired
    private ReserverRepository reserverRepository;


    /****************ADMIN****************/
    
    /*Voir les réservations d'un voyage POST*/
    @PostMapping("/reservations-Voyage/{id}")
    public String afficherListeVoyages(@PathVariable Long id, HttpServletRequest request, Model model) {
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }

	    Voyage voyage = voyageRepository.findById(id).orElse(null);
	    Iterable<Reserver> reservations = reserverRepository.findByVoyageVoyageId(id);
	    
	    model.addAttribute("reservations", reservations);
	    model.addAttribute("voyage", voyage);
	    return "admin/liste_reservations_voyage";
	}
    /*Voir les réservations d'un voyage GET*/
    @GetMapping("/reservation-Voyage/{id}")
    public String afficherListeVoyages2(@PathVariable Long id, HttpServletRequest request, Model model) {
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }

	    Voyage voyage = voyageRepository.findById(id).orElse(null);
	    Iterable<Reserver> reservations = reserverRepository.findByVoyageVoyageId(id);
	    
	    model.addAttribute("reservations", reservations);
	    model.addAttribute("voyage", voyage);
	    return "admin/liste_reservations_voyage";
	}
    
    /*Annuler une réservation*/
    @PostMapping("/annulation")
    public String annulerVoyage(HttpServletRequest request, Model model, @RequestParam("reservationId") Long reservationId) {

        extractTokenInfo(request, model);
        
        /*Rajouter les places à la destination*/
        Reserver res = reserverRepository.findById(reservationId).orElse(null);
        res.getVoyage().setNbPlaces(res.getNbPersonnes() + res.getVoyage().getNbPlaces());

        /*Supprimer reservation*/
        reserverRepository.deleteById(reservationId);
        
        /*Si admin*/
        Boolean role = (Boolean) model.getAttribute("role");
        if(role) {
        	return "redirect:/reservation-Voyage/" +res.getVoyage().getVoyageId();
        }
        /*Client*/
    	return "redirect:/profil-Client";
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