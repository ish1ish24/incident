package com.example.demo.service;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Incident;
import com.example.demo.dto.Role;
import com.example.demo.dto.User;
import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.repository.IncidentRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository; // Inject RoleRepository
    
    @Autowired
    IncidentRepository incidentRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean registerUser(RegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return false; // Username already exists
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return false; // Username already exists
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry()); 
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setFax(request.getFax());
        user.setMobileno(request.getMobileno());
        user.setPincode(request.getPincode());
        user.setConfirmPassword(request.getConfirmPassword());
        
        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName("USER"); // Fetch Role by name from database

        if (defaultRole == null) {
            // Handle case where "USER" role doesn't exist.
            // You might want to create the role here, or return an error.
            defaultRole = new Role();
            defaultRole.setName("USER");
            roleRepository.save(defaultRole); //create a new role.
        }

        roles.add(defaultRole);
        user.setRoles(roles);

        userRepository.save(user);
        return true;
    }
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return userRepository.getUserByUsername(authentication.getName());
        }
        return null; // Return null if no user is logged in
    }
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	
	public ResponseEntity<?> saveIncident(Incident incident) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    User user = userRepository.getUserByUsername(authentication.getName());
	    incident.setUserName(user.getUsername());

	    // Generate unique 5-digit incident ID
	    String uniqueId = generateUniqueIncidentId();
	    incident.setIncidentId(uniqueId); // assuming incidentId is an Integer field

	    incidentRepository.save(incident);

	    return ResponseEntity.ok("Incident saved");
	}
	
	public ResponseEntity<?> getIncidentForUser() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user=userRepository.getUserByUsername(authentication.getName());
	      List<Incident> incident =incidentRepository.findByUserName(user.getUsername());
	  //    List<Incident> incident1 =incidentRepository.findAll();
		return ResponseEntity.ok(incident);
	}
	
	 public Incident editIncident(Long id, Incident updatedIncident) {
	        Optional<Incident> optionalIncident = incidentRepository.findById(id);
	        if (optionalIncident.isPresent()) {
	            Incident incident = optionalIncident.get();
	            incident.setAddress(updatedIncident.getAddress());
	            incident.setDateAndTime(updatedIncident.getDateAndTime());
	            incident.setIncidentType(updatedIncident.getIncidentType());
	            return incidentRepository.save(incident);
	        } else {
	            throw new RuntimeException("Incident not found with id " + id);
	        }
	 }
	 public void deleteIncident(Long id) {
		    if (incidentRepository.existsById(id)) {
		        incidentRepository.deleteById(id);
		    } else {
		        throw new RuntimeException("Incident not found with id " + id);
		    }
   } 
	 private String generateUniqueIncidentId() {
		    Random random = new Random();
		    String incidentId;
		    int currentYear = Year.now().getValue(); // Automatically gets current year

		    do {
		        int randomNum = 10000 + random.nextInt(90000); // 5-digit number
		        incidentId = "RMG" + randomNum + currentYear;
		    } while (incidentRepository.existsByIncidentId(incidentId));

		    return incidentId;
		}
	 
} 