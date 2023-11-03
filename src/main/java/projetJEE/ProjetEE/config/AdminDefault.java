package projetJEE.ProjetEE.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Repersitory.UtilisateurRepository;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.annotation.PostConstruct;

@Component
public class AdminDefault {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostConstruct
    public void createInitialUser() {
        // On vérifie que l'utilisateur existe
        Iterable<Utilisateur> temp = utilisateurRepository.findByEmail("admin@admin.fr");
        List<Utilisateur> existe = new ArrayList<>();
        temp.forEach(existe::add);
        
        // L'utilisateur n'existe pas
        if (existe.size() == 0) {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom("admin");
            utilisateur.setPrenom("admin");
            utilisateur.setEmail("admin@admin.fr");
            utilisateur.setAdmin(true);
            String motDePasse = "admin2023";
            String motDePasseHache = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

            utilisateur.setMdp(motDePasseHache);

            utilisateurRepository.save(utilisateur);
        }
    }
}
