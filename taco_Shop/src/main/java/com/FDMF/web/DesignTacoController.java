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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.FDMF.model.Ingredient;
import com.FDMF.model.Ingredient.Type;
import com.FDMF.model.Order;
import com.FDMF.model.Taco;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		prepareData(model);
		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream()
			.filter(i -> i.getType().equals(type))
			.collect(Collectors.toList());
	}
	
	@ModelAttribute(name = "tktn")
	public Taco taco() {
		return new Taco();
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute(name = "tktn") Taco design, Errors errors, Model model, @ModelAttribute Order order) {
		
		if(errors.hasErrors()) {
			log.info("Error de usuario");
			prepareData(model);
			return "design";
		}
		order.addDesign(design);
		log.info(design.toString());
		return "redirect:/orders/current";
	}
	
	private void prepareData(Model model) {
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
	}

}
