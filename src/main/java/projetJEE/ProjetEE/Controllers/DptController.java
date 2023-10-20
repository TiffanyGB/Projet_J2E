package projetJEE.ProjetEE.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import projetJEE.ProjetEE.Repersitory.DptRepository;

@Controller
public class DptController {

	@Autowired
	DptRepository dptRepository;
	
	@GetMapping(path = "/aa")
	public String ioupi(Model model) {
		System.out.println("ioupi");
//		
//		Department dpt = new Department();
//		dpt.setName("cytech");
//		dptRepository.save(dpt);
//		dpt = dptRepository.findByName("cytech").get();
//		
//		model.addAttribute("dpt", dpt);
		return "aa";
	}
}