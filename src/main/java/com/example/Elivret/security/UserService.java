package com.example.Elivret.security;


import com.example.Elivret.model.Person;
import com.example.Elivret.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@Profile("usejwt")
public class UserService {

	@Autowired
	private PersonRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	public String login(String username, String password) {
		try {
			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			var user = userRepository.findByUserName(username);
			System.out.println(user);
			return jwtTokenProvider.createToken(user);
		} catch (AuthenticationException e) {
			throw new MyJwtException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public String signup(Person user) {
		System.out.println(user);
		if (!userRepository.existsByUserName(user.getUserName())) {
			System.out.println(user.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
			return jwtTokenProvider.createToken(user);
		}else {
			throw new MyJwtException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	public void delete(String username) {
		userRepository.deleteByUserName(username);
	}

	public Person search(String username) {
		return userRepository.findByUserName(username);

	}

	public Person whoami(HttpServletRequest req) {
		return search(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
	}

	public String refresh(String username) {
		return jwtTokenProvider.createToken(userRepository.findByUserName(username));
	}



	public String logout(HttpServletRequest req) {
		String token = jwtTokenProvider.resolveToken(req);

		if( jwtTokenProvider.validateToken(token) ) {
			jwtTokenProvider.forgetToken(token);
			return token;
		}

		return null;
	}

}
