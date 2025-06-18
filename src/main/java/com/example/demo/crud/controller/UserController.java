package com.example.demo.crud.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.crud.model.UserDetailsModel;
import com.example.demo.crud.model.entity.UserDetails;
import com.example.demo.crud.service.UserService;

@CrossOrigin
@RestController
@RequestMapping
public class UserController {

	@Value("${base.upload.path}")
	private String uploadDir1;

	@Autowired
	private UserService userService;

	@GetMapping("/api/users")
	public List<UserDetails> getAllUsers() {
		return userService.getAllUsers();

	}

	@GetMapping("/healthCheck")
	public String handShake() {
		return "Hello";
	}

	@GetMapping("/api/users/{id}")
	public UserDetails getUser(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PostMapping(value = "/api/user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public UserDetails createUserDetails(@ModelAttribute UserDetailsModel user) {
		return userService.createUserDetails(user);
	}

	@PutMapping("/api/users/{id}")
	public UserDetails updateUserDetails(@PathVariable Long id, @RequestBody UserDetails userDetails) {
		return userService.updateUserDetails(id, userDetails);
	}

	@DeleteMapping("/api/users/{id}")
	public void deleteUserDetails(@PathVariable Long id) {
		userService.deleteUserDetails(id);
	}

}
