package projetJEE.ProjetEE.Controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
import jakarta.servlet.http.HttpServletResponse;
import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Repersitory.ReserverRepository;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;

@Controller
public class ProfilController {
    
    @Autowired
    private ReserverRepository reserverRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    /*Permet à un client connecté de voir ses infos et réservations*/
    @GetMapping("/profil-Client")
    public String profilCLient(HttpServletRequest request, 
    		@RequestParam(name = "id", required = false) Long id,
    		Model model) {
    	/*Extraire infos token*/
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil*/
	    if (model.asMap().isEmpty() || (model.getAttribute("role") == null) || (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }
	    Utilisateur user;
	    
	    /*Chercher les infos du client*/
	    if(id == null) {
		    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
	    	Iterator<Utilisateur> iterator = tmp.iterator();
		    user = iterator.next();
	    }else {
	    	user = utilisateurRepository.findById(id).orElse(null);
	    }
	    
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
        cookie.setMaxAge(0); 
        cookie.setPath("/"); 
        response.addCookie(cookie);
    	
    	return "redirect:/";
    }
    
    @PostMapping("/modifier-compte-redirection")
    public String modifierProfilRedirection(HttpServletRequest request,  
    		Model model, 
    		HttpServletResponse response,
    		@RequestParam Long userId) {
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil*/
	    if ((model.getAttribute("role") == null) || (boolean) model.getAttribute("role"))  {
	        return "redirect:/";
	    }
	    
	    Utilisateur user = utilisateurRepository.findById(userId).orElse(null);
	    model.addAttribute("user", user);
    	
    	return "client/modifierProfil";
    }
    
    @PostMapping("/modifier-profil")
    public String modifierProfil(HttpServletRequest request,  
    		Model model, 
    		@ModelAttribute Utilisateur user,
    		@RequestParam Long userId) {
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil*/
	    if ((model.getAttribute("role") == null) || (boolean) model.getAttribute("role"))  {
	        return "redirect:/";
	    }
	    
	    Utilisateur userExistant = utilisateurRepository.findById(userId).orElse(null);

	    /*Modification des infos*/
        if (userExistant != null) {
            userExistant.setNom(user.getNom());
            userExistant.setPrenom(user.getPrenom());
            if(user.getMdp() != "") {
                String motDePasse = user.getMdp(); 
                String motDePasseHache = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
                userExistant.setMdp(motDePasseHache);
            }
    	    
            utilisateurRepository.save(userExistant);
            Long id = userExistant.getIdUtilisateur();
        	return "redirect:/profil-Client?id=" + id;
        }
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