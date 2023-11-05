package projetJEE.ProjetEE.Controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Reserver;
import projetJEE.ProjetEE.Models.Utilisateur;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repository.CategorieRepository;
import projetJEE.ProjetEE.Repository.ReserverRepository;
import projetJEE.ProjetEE.Repository.UtilisateurRepository;
import projetJEE.ProjetEE.Repository.VoyageRepository;


@Controller
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private VoyageRepository voyageRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private ReserverRepository reserverRepository;

    /****************ADMIN****************/
   
    /*Permet de voir la liste des categories et formulaire d'ajout*/
    @GetMapping("/listeCategories")
    public String afficherCategories(HttpServletRequest request, Model model) {
		extractTokenInfo(request, model);
		
	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }
		((Model) model).addAttribute("categories", categorieRepository.findAll());

		return "admin/liste_catégories_admin";
	}


    @PostMapping("/ajouter-categorie")
    public String ajouterCategorie(@ModelAttribute Categorie categorie,
    		@RequestParam("nomCategorie") String nomCategorie, 
    		@RequestParam("file") MultipartFile file) throws IOException {
    	
	    if (file != null && !file.isEmpty()) {
	        // Compression de l'image
	        byte[] bytes = file.getBytes();

	        categorie.setImageCategorie(bytes);
	    }
	    
    	categorieRepository.save(categorie); 

        /*Retour à la liste des catégories*/
    	return "redirect:/listeCategories";
	}
    
    
    @PostMapping("/supprimer-categorie")
    public String supprimerVoyage(@RequestParam Long idCategorie) {
        categorieRepository.deleteById(idCategorie);
        
        /*Retour à la liste des catégories*/
        return "redirect:/listeCategories";
    }
    
    @PostMapping("/modifier-categorie-redirection")
    public String modifierCategorieRedirection(@RequestParam Long idCategorie,  HttpServletRequest request, Model model) {
        extractTokenInfo(request, model);
        
	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    }   	
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        model.addAttribute("categorie", categorie);

        return "admin/modifier_categorie_admin";
    }
    
    @PostMapping("/modifier-categorie")
    public String modifierCategorie(HttpServletRequest request, Model model,
    		@ModelAttribute Categorie categorie,
    		@RequestParam("image") MultipartFile file) throws IOException{
    	
        extractTokenInfo(request, model);
        
	    /*Mauvais profil, il faut etre admin*/
	    if ((model.getAttribute("role") == null) || !(boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 
	    
	    /*Recherche des infos de la categorie à modifier*/
        Categorie categorieExistant = categorieRepository.findById(categorie.getIdCategorie()).orElse(null);
        if (categorieExistant != null) {
        	//Modifier le nom
            categorieExistant.setNomCategorie(categorie.getNomCategorie());
    	    if (file != null && !file.isEmpty()) {
    	        // Compression de l'image
    	        byte[] bytes = file.getBytes();
    	        categorieExistant.setImageCategorie(bytes);
    	    }
    	    //Enregistrement
            categorieRepository.save(categorieExistant);
        }
            
        return "redirect:/listeCategories";
    }   
    
    /****************CLIENT****************/
    
    /*Liste des catégories pour le client*/
    @GetMapping("/categories-client")
    public String afficherCategorieClient(HttpServletRequest request,Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
	    extractTokenInfo(request, model);
	    
	    /*Mauvais profil, il faut etre client connecté ou non*/
	    if ((model.getAttribute("role") != null) && (boolean) model.getAttribute("role")) {
	        return "redirect:/";
	    } 

		return "client/liste_categories_client";
	}
    
    /*Tous les voyages d'une catégorie choisie*/
    @GetMapping("/categorie-voyages/{id}")
    public String categorieDetails(HttpServletRequest request,@PathVariable Long id, Model model) {
		extractTokenInfo(request, model);
	    
        Categorie categorie = categorieRepository.findById(id).orElse(null);
        List<Voyage> voyagesDeLaCategorie = voyageRepository.findByIdCategorie(categorie);
        Map<Long, Boolean> voyagesWithReservationStatus = new HashMap<>();
        
        //S'il y a un token
	    if (!model.asMap().isEmpty()) {
	    	
		    /*Rechercher client*/
		    Iterable<Utilisateur> tmp = utilisateurRepository.findByEmail((String) model.getAttribute("email"));
	    	Iterator<Utilisateur> iterator = tmp.iterator();
		    Utilisateur user = iterator.next();
	    	
		    /*Déterminer les voyages déjà réservés par celui-ci*/
	        for (Voyage voyage : voyagesDeLaCategorie) {
	            List<Reserver> reservations = reserverRepository.findByUtilisateurAndVoyage(user, voyage);
	            boolean hasReservation = !reservations.isEmpty();
	            voyagesWithReservationStatus.put(voyage.getVoyageId(), hasReservation);
	        } 
	        
	        model.addAttribute("voyagesWithReservationStatus", voyagesWithReservationStatus);

	    }else {
	        for (Voyage voyage : voyagesDeLaCategorie) {
	            voyagesWithReservationStatus.put(voyage.getVoyageId(), false);
	        } 
	    }        
        
        model.addAttribute("voyages", voyagesDeLaCategorie);
        model.addAttribute("categorie", categorie);

        return "client/infos_categorie";
    }
    
    
    /*Accuei*/
    @GetMapping("/")
    public String accueil(HttpServletRequest request, Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
		
	    extractTokenInfo(request, model);

		return "index";
	}
    

    
    @Controller
    public class UploadController {

        @PostMapping("/upload")
        public String handleFileUpload(@RequestParam("file") MultipartFile file) {
            if (!file.isEmpty()) {
                try {
                    // Récupérez le nom du fichier
                    String fileName = file.getOriginalFilename();

                    String uploadDir = "/home/cytech/Cours/Ing2/S1/J2E/ProjetEE/src/main/resources/static/img/";

                    // Créez le fichier de destination
                    File dest = new File(uploadDir + fileName);

                    // Copiez le fichier téléchargé dans le dossier de destination
                    file.transferTo(dest);

                    // Faites ce que vous souhaitez avec le fichier, par exemple, sauvegardez le chemin dans la base de données
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "redirect:/";
        }
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