package com.example.Elivret.controller;


import com.example.Elivret.model.Person;
import com.example.Elivret.model.PersonDTO;
import com.example.Elivret.security.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * L'API d'authentification
 */
@RestController
@RequestMapping("/secu-users")
@CrossOrigin( origins = "*", maxAge = 3600 , exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@Profile("usejwt")
public class UserController {

	@Autowired
	private UserService userService;

	private ModelMapper modelMapper = new ModelMapper();

	/**
	 * Authentification et récupération d'un JWT
	 */
	@PostMapping("/login")
	public String login(//
			@RequestBody PersonDTO person) {
		System.out.println(person);
		return userService.login(person.getUserName(), person.getPassword());
	}

	/**
	 * Ajouter un utilisateur
	 */
	@PostMapping("/signup")
	public String signup(@RequestBody PersonDTO user) {
		return userService.signup(modelMapper.map(user, Person.class));
	}

	/**
	 * Supprimer un utilisateur
	 */
	@DeleteMapping("/{username}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String delete(@PathVariable String username) {
		System.out.println("delete " + username);
		userService.delete(username);
		return username;
	}

	/**
	 * Récupérer des informations sur un utilisateur
	 */
	@GetMapping("/{username}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public PersonDTO search(@PathVariable String username) {
		return modelMapper.map(userService.search(username), PersonDTO.class);
	}

	/**
	 * Récupérer des informations sur l'utilisateur courant
	 */
	@GetMapping(value = "/me")
	public Person whoami(HttpServletRequest req) {
		return userService.whoami(req);
	}

	/**
	 * Récupérer un nouveau JWT
	 */
	@GetMapping("/refresh")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}
	
	/**
	 * Logout
	 */
	@GetMapping("/logout")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public String logout(HttpServletRequest req) {
		return userService.logout(req);
	}

}
