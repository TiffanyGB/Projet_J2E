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
import jakarta.servlet.http.HttpServletResponse;
import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.CategorieRepository;
import projetJEE.ProjetEE.Repersitory.ReserverRepository;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class ProfilController {
    
    @Autowired
    private ReserverRepository reserverRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    /****************CLIENT****************/
    @GetMapping("/profil-Client")
    public String profilCLient(HttpServletRequest request, Model model) {
    	/*Extraire infos token*/
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil*/
	    if (model.asMap().isEmpty() || (model.getAttribute("role") == null) || (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }

	    /*Chercher les infos du client*/
	    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
    	Iterator<Utilisateur> iterator = tmp.iterator();
	    Utilisateur user = iterator.next();
	    
	    /*Chercher les réservations du client*/
	    List<Reserver> reservations = reserverRepository.findByUtilisateur(user);
	    
	    model.addAttribute("user", user);
	    model.addAttribute("reservations", reservations);
	    
		return "client/profilClient";
	}
    
    @PostMapping("/supprimer-compte")
    public String supprimerCompte(HttpServletRequest request,  Model model, HttpServletResponse response) {
	    extractTokenInfo(request, model);
	    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
    	Iterator<Utilisateur> iterator = tmp.iterator();
	    Utilisateur user = iterator.next();
	    utilisateurRepository.delete(user);
	    
        // Supprimez le cookie en définissant un cookie expiré
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0); // Définissez la durée de vie du cookie à 0 pour le supprimer
        cookie.setPath("/"); // Assurez-vous que le chemin du cookie correspond à celui qui a été défini lors de la création
        response.addCookie(cookie);
    	
    	return "redirect:/";
    }
    
    @PostMapping("/modifier-compte")
    public String modifierProfil(HttpServletRequest request,  Model model, HttpServletResponse response) {
	    extractTokenInfo(request, model);
	    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
    	Iterator<Utilisateur> iterator = tmp.iterator();
	    Utilisateur user = iterator.next();
	    utilisateurRepository.delete(user);
	    
        // Supprimez le cookie en définissant un cookie expiré
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0); // Définissez la durée de vie du cookie à 0 pour le supprimer
        cookie.setPath("/"); // Assurez-vous que le chemin du cookie correspond à celui qui a été défini lors de la création
        response.addCookie(cookie);
    	
    	return "redirect:/";
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