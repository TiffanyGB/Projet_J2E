package projetJEE.ProjetEE.Controllers;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

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
import jakarta.servlet.http.HttpServletResponse;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;



@Controller
public class InscriptionController {
	
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /****************ADMIN****************/
   
    @GetMapping("/inscription")
    public String inscriptionGET(Model model, @RequestParam(name = "error", required = false) String error) {
  
        if (error != null) {
        	model.addAttribute("error", error);
        }
        
		return "client/inscription";
	}

    
    @PostMapping("/inscription-enregistrer")
    public String enregistrerUtilisateur(@ModelAttribute Utilisateur user,
    		HttpServletResponse response,
    		Model model) {
    	
    	
    	/*Vérifier que l'adresse mail est unique*/
    	String email = user.getEmail();
    	Iterable<Utilisateur> listeUtilisateurs = utilisateurRepository.findByEmail(email);
    	
    	/*Email existante*/
    	if(listeUtilisateurs.iterator().hasNext()) {
    		String error = "Adresse mail déjà prise";
            return "redirect:/inscription?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
    	}
    	user.setAdmin(false);
    	
    	// Hacher le mot de passe avant de l'enregistrer
        String motDePasse = user.getMdp(); // Mot de passe en texte clair
        String motDePasseHache = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
        user.setMdp(motDePasseHache);
    	
    	utilisateurRepository.save(user);
    	
        String token = Jwts.builder()
                .setSubject(user.getNom())
                .claim("role", false)
                .claim("email", user.getEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "abcdef")
                .compact();
        
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setMaxAge(3600);
        response.addCookie(tokenCookie);

        
        return "redirect:/"; 
    }
    
   

}