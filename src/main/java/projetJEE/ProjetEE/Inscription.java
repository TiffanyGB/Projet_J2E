package projetJEE.ProjetEE;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
//@RequestMapping(path = "/inscription")
public class Inscription {

    @GetMapping("/cc")
    public String afficherFormulaireInscription(Model model) {
        return "confirmation"; // Le nom de la page JSP (sans l'extension .jsp)
    }

    @PostMapping(path ="/enregistrer")
    public String enregistrerUtilisateur(Model model, String nom, String email) {
        // Traitez les données du formulaire ici
        // Enregistrez l'utilisateur, effectuez des vérifications, etc.
        // Redirigez vers une page de confirmation ou de succès
        return "confirmation"; // Le nom de la page de confirmation JSP
    }

}
