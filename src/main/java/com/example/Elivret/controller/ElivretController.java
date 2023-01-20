package com.example.Elivret.controller;

import com.example.Elivret.model.AppUserRole;
import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import com.example.Elivret.service.ElivretService;
import com.example.Elivret.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin( origins = "*", maxAge = 3600 , exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
public class ElivretController {

	@Autowired
	private ElivretService elivretService;

	@Autowired
	private PersonService personService;


	@PostMapping("/add")
	public ResponseEntity createPost(@RequestBody Elivret elivret) {
		elivretService.createElivret(elivret);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Elivret>> showAllPosts() {
		return new ResponseEntity<>(elivretService.findAllElivrets(), HttpStatus.OK);
	}

	@PutMapping ("/elivret/{elivretId}")
    public ResponseEntity updateElivret(@PathVariable(value = "elivretId") Long elivretId,
                                        @RequestBody String title) {
        elivretService.updateElivret(elivretId,title);
        return new ResponseEntity(HttpStatus.OK);
    }

	@GetMapping("/elivret/{elivretId}")
	public ResponseEntity<Elivret> showElivretById(@PathVariable(value = "elivretId") Long elivretId) {
		Elivret elivret = elivretService.getElivretById(elivretId);
		return new ResponseEntity<>(elivret, HttpStatus.OK);
	}

	@DeleteMapping("/elivret/{elivretId}")
	public ResponseEntity<Elivret> deleteElivretById(@PathVariable(value = "elivretId") Long elivretId) {
		elivretService.deleteById(elivretId);
		return new ResponseEntity<>(HttpStatus.OK);
	}


	@PostConstruct
	public void init() {
		//create Admin
		Set<String> roles = new HashSet<String>();
		roles.add( String.valueOf(AppUserRole.ROLE_ADMIN) );

		Person p = new Person(1L, "admin", "admin", "admin", "admin", "admin", roles, "test" );
		personService.createPerson(p);
	}

}