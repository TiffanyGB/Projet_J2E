package projetJEE.ProjetEE.Controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;


@Controller
public class ConnexionController {
	
    @Autowired
    private UtilisateurRepository utilisateurRepository;
   
    @GetMapping("/connexion")
    public String connexionGET(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
        	model.addAttribute("error", error);
        }
		return "connexion";
	}

    
    @PostMapping("/connexion-verif")
    public String connexionPost(@ModelAttribute Utilisateur user, 
    		HttpServletResponse response, 
    		Model model,
    		HttpServletRequest request) {
    	List<Utilisateur> existe = utilisateurRepository.findByEmailAndMdp(user.getEmail(), user.getMdp());
    	
    	/*identifiants corrects*/
    	if(existe.size() > 0) {
        	Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail(user.getEmail());
        	Iterator<Utilisateur> iterator = tmp.iterator();
    	    Utilisateur premierUtilisateur = iterator.next();
    	    
            String token = Jwts.builder()
                    .setSubject(premierUtilisateur.getNom())
                    .claim("role", premierUtilisateur.getAdmin())
                    .claim("email", premierUtilisateur.getEmail())
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "abcdef")
                    .compact();
            
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(3600);
            response.addCookie(tokenCookie);
            
            return "redirect:/";
    	}
    	/*Identifiants incorrects*/
    	String error = "Email ou mot de passe incorrect.";
    	return "redirect:/connexion?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
    	
    }
    
   

}