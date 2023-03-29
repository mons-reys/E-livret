package com.example.Elivret.controller;

import com.example.Elivret.model.AppUserRole;
import com.example.Elivret.model.Elivret;
import com.example.Elivret.model.Person;
import com.example.Elivret.model.Section;
import com.example.Elivret.service.ElivretService;
import com.example.Elivret.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity createPost(@RequestBody Elivret elivret) {
		elivretService.createElivret(elivret);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Elivret>> showAllPosts() {
		List<Elivret> elivrets = elivretService.findAllElivrets();
		return new ResponseEntity<>(elivrets, HttpStatus.OK);
	}

	@PutMapping ("/elivret/{elivretId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity updateElivret(@PathVariable(value = "elivretId") Long elivretId,
                                        @RequestBody String title) {
        elivretService.updateElivret(elivretId,title);
        return new ResponseEntity(HttpStatus.OK);
    }

	@GetMapping("/elivret/{elivretId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or @securityRoleService.isOwner(#elivretId,authentication)")
	public ResponseEntity<Elivret> showElivretById(@PathVariable(value = "elivretId") Long elivretId) {
		Elivret elivret = elivretService.getElivretById(elivretId);
		return new ResponseEntity<>(elivret, HttpStatus.OK);
	}

	@DeleteMapping("/elivret/{elivretId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Elivret> deleteElivretById(@PathVariable(value = "elivretId") Long elivretId) {
		elivretService.deleteById(elivretId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping ("/elivret/{elivretId}/invite")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('Tuteur')")
	public ResponseEntity inviteToFillSection(HttpServletRequest request, @PathVariable(value = "elivretId") Long elivretId,
											  @RequestBody Person person) {

		String origin = request.getHeader(HttpHeaders.ORIGIN);
		String url = personService.registerPersonWithLivret(person, elivretId, origin);
		System.out.println(url);

		return new ResponseEntity(url, HttpStatus.OK);
	}

	@GetMapping("/elivret/{elivretId}/take")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Elivret>> takeElivret(@PathVariable(value = "elivretId") Long elivretId) {
		Elivret elivret = elivretService.getElivretById(elivretId);
		List<Elivret> elivrets = new ArrayList<Elivret>();
		elivrets.add(elivret);
		return new ResponseEntity<>(elivrets, HttpStatus.OK);
	}

	@GetMapping("elivret/person/{personId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or @securityRoleService.isTheAuthenticatedUser(#personId,authentication)")
	public ResponseEntity<List<Elivret>> getPeronLivrets(@PathVariable(value = "personId") Long personId){
		List<Elivret> elivrets = elivretService.getElivretByPersonId(personId);
		return new ResponseEntity<>(elivrets, HttpStatus.OK);
	}



	@PostConstruct
	public void init() {
		//create Admin
		Set<String> roles = new HashSet<String>();
		roles.add( String.valueOf(AppUserRole.ROLE_ADMIN) );

		Person admin = new Person(1L, "admin", "admin", "admin@gmail.com", passwordEncoder.encode("admin"), "admin", roles, "test" );
		personService.createPerson(admin);


		Set<String> role_etudiant = new HashSet<String>();
		role_etudiant.add(String.valueOf(AppUserRole.Alternant));

		Person etudiant = new Person(null, "john", "adam", "admin", passwordEncoder.encode("admin"), "etudiant", role_etudiant, "etudiant");
		personService.createPerson(etudiant);


		Set<String> role_etudiant2 = new HashSet<String>();
		role_etudiant2.add(String.valueOf(AppUserRole.Alternant));

		Person etudiant2 = new Person(null, "john", "adam", "admin", passwordEncoder.encode("admin"), "etudiant2", role_etudiant2, "etudiant");
		personService.createPerson(etudiant2);


		List<Person> persons = new ArrayList<>();
		persons.add(admin);
		persons.add(etudiant);

		Elivret elivret = new Elivret(null, "livret test 1", persons);
		elivretService.createElivret(elivret);
		System.out.println(elivret);
	}

}