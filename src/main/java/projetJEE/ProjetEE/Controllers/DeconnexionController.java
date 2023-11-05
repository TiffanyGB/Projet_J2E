package projetJEE.ProjetEE.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class DeconnexionController {
	
   
    @GetMapping("/deconnexion")
    public String deconnexion(HttpServletResponse response, Model model) {
        /*Suppression des cookies pour effacer toke,*/
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0); 
        cookie.setPath("/");
        response.addCookie(cookie);

        /*Page d'accueil*/
        return "redirect:/";
    }

    

    
   

}