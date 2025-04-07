package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Incident;
import com.example.demo.dto.User;
import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.service.UserService;




@RestController
@RequestMapping("/api")
public class UserController {
	
	 @Autowired
	    private UserService userService;

	    @PostMapping("/register")
	    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
	        boolean registered = userService.registerUser(request);
	        if (registered) {
	            return ResponseEntity.ok("User registered successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
	        }
	    }
	    @GetMapping("/loggeduser")
	    public ResponseEntity<User> getLoginUser() {
	    	if(userService.getLoggedInUser()==null) {
	    		ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not login yet");
	    	}
	    	 return ResponseEntity.ok(userService.getLoggedInUser());
	        }
	    
	    @PostMapping("/saveIncident")
	    public ResponseEntity<?> saveIncoident(@RequestBody Incident incident) {
	    	 return ResponseEntity.ok(userService.saveIncident(incident));
	        }
	    
	    @GetMapping("/getIncident")
	    public ResponseEntity<?> getIncidentforUser() {
	    	 return ResponseEntity.ok(userService.getIncidentForUser());
	        }

@GetMapping("/getAllUser")
public ResponseEntity<List<User>> getAllUser() {
	 return ResponseEntity.ok(userService.getAllUser());
    }
@PutMapping("/edit/{id}")
public Incident updateIncident(@PathVariable Long id, @RequestBody Incident updatedIncident) {
    return userService.editIncident(id, updatedIncident);
}
@DeleteMapping("/delete/{id}")
public String deleteIncident(@PathVariable Long id) {
    userService.deleteIncident(id);
    return "Incident with ID " + id + " has been deleted successfully.";
}

}


	    // Login is handled automatically by Spring Security's form login
	
