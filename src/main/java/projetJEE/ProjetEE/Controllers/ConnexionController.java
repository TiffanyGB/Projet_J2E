package projetJEE.ProjetEE.Controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.security.crypto.bcrypt.BCrypt;


@Controller
public class ConnexionController {
	
    @Autowired
    private UtilisateurRepository utilisateurRepository;
   
    @GetMapping("/connexion")
    public String connexionGET(@RequestParam(name = "error", required = false) String error, Model model) {
    	
    	/*Si erreur de connexion présente renvoyer celle-ci à la vue*/
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
    	
    	
        Iterable<Utilisateur> temp = utilisateurRepository.findByEmail(user.getEmail());
        List<Utilisateur> existe = new ArrayList<>();
        temp.forEach(existe::add);
        
    	/*identifiants corrects*/
    	if(existe.size() > 0) {
    		 Utilisateur premierUtilisateur = existe.get(0);
    		 
    		 /*Mdp haché*/
    		 String motDePasseHacheEnBase = premierUtilisateur.getMdp();
    		 /*Vérifier que le mdp correspond*/
    		 if (BCrypt.checkpw(user.getMdp(), motDePasseHacheEnBase)) {
    		 
    		/*Création du token*/
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
    	}
    	/*Identifiants incorrects*/
    	String error = "Email ou mot de passe incorrect.";
    	return "redirect:/connexion?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
    	
    }
 
}