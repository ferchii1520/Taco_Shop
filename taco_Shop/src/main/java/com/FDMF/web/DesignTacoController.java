package com.FDMF.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.FDMF.model.Ingredient;
import com.FDMF.model.Ingredient.Type;
import com.FDMF.model.Taco;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Type.CHEESE),
				new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Type.SAUCE),
				new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
				);
		
		Type[] types = Ingredient.Type.values();
		
		for(Type type: types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		
		model.addAttribute("tktn", new Taco());
		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream()
			.filter(i -> i.getType().equals(type))
			.collect(Collectors.toList());
	}
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute(name = "tktn") Taco design, Errors errors) {
		
		if(errors.hasErrors()) {
			log.info("Error de usuario");
		}
		log.info(design.toString());
		return null;
		
	}

}
