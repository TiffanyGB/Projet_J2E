package projetJEE.ProjetEE.Controllers;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.hibernate.sql.ast.tree.expression.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;


@Controller
public class InscriptionController {
	
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /****************ADMIN****************/
   
    @GetMapping("/inscription")
    public String inscriptionGET() {
		return "client/inscription";
	}

    
    @PostMapping("/inscription-enregistrer")
    public String enregistrerUtilisateur(@ModelAttribute Utilisateur user, HttpServletResponse response) {
    	user.setAdmin(false);
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

        
        return "redirect:/?token=" + token; 
    }
    
   

}