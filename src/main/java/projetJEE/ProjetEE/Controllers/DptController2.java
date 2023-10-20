package projetJEE.ProjetEE.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import projetJEE.ProjetEE.DptRepository;

@Controller
public class DptController2 {

	@Autowired
	DptRepository dptRepository;
	
	@GetMapping(path = "/bb")
	public String bb(Model model) {
//		
//		Department dpt = new Department();
//		dpt.setName("cytech");
//		dptRepository.save(dpt);
//		dpt = dptRepository.findByName("cytech").get();
//		
//		model.addAttribute("dpt", dpt);
		return "bb";
	}
}