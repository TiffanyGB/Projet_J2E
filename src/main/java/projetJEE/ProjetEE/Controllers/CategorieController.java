package projetJEE.ProjetEE.Controllers;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import projetJEE.ProjetEE.Models.Categorie;
import projetJEE.ProjetEE.Models.Voyage;
import projetJEE.ProjetEE.Repersitory.CategorieRepository;
import projetJEE.ProjetEE.Repersitory.VoyageRepository;

@Controller
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private VoyageRepository voyageRepository;

    /****************ADMIN****************/
   
    @GetMapping("/listeCategories")
    public String afficherCategories(Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
		return "admin/liste_catégories_admin";
	}

    

    
    
    @PostMapping("/supprimer-categorie")
    public String supprimerVoyage(@RequestParam Long idCategorie) {
        categorieRepository.deleteById(idCategorie);
        return "redirect:/listeCategories";
    }
    
    @PostMapping("/modifier-categorie-redirection")
    public String modifierCategorieRedirection(@RequestParam Long idCategorie,  Model model) {
    	
        Categorie categorie = categorieRepository.findById(idCategorie).orElse(null);
        model.addAttribute("categorie", categorie);
        return "admin/modifier_categorie_admin";
    }
    
    @PostMapping("/modifier-categorie")
    public String modifierCategorie(@ModelAttribute Categorie categorie, @RequestParam("imageCategorie") MultipartFile imageCategorie) {
    	
        Categorie categorieExistant = categorieRepository.findById(categorie.getIdCategorie()).orElse(null);
        System.out.println(categorie);
        if (categorieExistant != null) {
//        	categorieExistant.setImageCategorie(categorie.getImageCategorie());
            categorieExistant.setNomCategorie(categorie.getNomCategorie());
            categorieRepository.save(categorieExistant);
            }
            
        return "redirect:/listeCategories";
    }   
    
    /****************CLIENT****************/
    @GetMapping("/categories-client")
    public String afficherCategorieClient(Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
		return "client/liste_categories_client";
	}
    
    @GetMapping("/categorie-voyages/{id}")
    public String categorieDetails(@PathVariable Long id, Model model) {
        Categorie categorie = categorieRepository.findById(id).orElse(null);

        List<Voyage> voyagesDeLaCategorie = voyageRepository.findByIdCategorie(categorie);
        model.addAttribute("voyages", voyagesDeLaCategorie);
        model.addAttribute("categorie", categorie);
    
        return "client/infos_categorie";
    }
    
    @GetMapping("/")
    public String accueil(Model model) {
		((Model) model).addAttribute("categories", categorieRepository.findAll());
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
    
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}