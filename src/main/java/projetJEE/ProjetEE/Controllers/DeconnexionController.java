package projetJEE.ProjetEE.Controllers;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
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
public class DeconnexionController {
	
   
    @GetMapping("/deconnexion")
    public String deconnexion(HttpServletResponse response, Model model) {
        // Supprimez le cookie en définissant un cookie expiré
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0); // Définissez la durée de vie du cookie à 0 pour le supprimer
        cookie.setPath("/"); // Assurez-vous que le chemin du cookie correspond à celui qui a été défini lors de la création
        response.addCookie(cookie);

        // Redirigez l'utilisateur vers la page d'accueil ou une autre page de votre choix
        return "redirect:/";
    }

    

    
   

}