package com.mr.rc.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mr.rc.springjwt.exception.UserNotFoundException;
import com.mr.rc.springjwt.models.ERole;
import com.mr.rc.springjwt.models.GenericResponse;
import com.mr.rc.springjwt.models.MailVerificationToken;
import com.mr.rc.springjwt.models.PasswordResetToken;
import com.mr.rc.springjwt.models.Role;
import com.mr.rc.springjwt.models.User;
import com.mr.rc.springjwt.payload.request.LoginRequest;
import com.mr.rc.springjwt.payload.request.SignupRequest;
import com.mr.rc.springjwt.payload.response.JwtResponse;
import com.mr.rc.springjwt.payload.response.MessageResponse;
import com.mr.rc.springjwt.repository.MailVerificationTokenRepo;
import com.mr.rc.springjwt.repository.PasswordResetTokenRepo;
import com.mr.rc.springjwt.repository.RoleRepository;
import com.mr.rc.springjwt.repository.UserRepository;
import com.mr.rc.springjwt.security.jwt.JwtUtils;
import com.mr.rc.springjwt.security.services.UserDetailsImpl;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordResetTokenRepo passwordTokenRepository;

	@Autowired
	MailVerificationTokenRepo mvtRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
    private JavaMailSender mailSender;

	@Autowired
	JwtUtils jwtUtils;

	
//SIGN IN FUNCTION
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		User user =  userRepository.findByUsername(userDetails.getUsername()).orElse(null);
		
		try
		{
			if(user.isMailVerfication()) {
				return ResponseEntity.ok(
						new JwtResponse(jwt, userDetails.getFirstName() + userDetails.getLastName(), userDetails.getId(), userDetails.getUsername(), userDetails.getMobile(), userDetails.getEmail(), roles));
			}
			else {
				return ResponseEntity.badRequest().body(new MessageResponse("Error: Activate Account by clicking on link sent to your e-Mail Id"));
			}
		}
		catch(NullPointerException np) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid User Name"));
		}
		catch(Exception ex) {
		}
		return null;
	}

//SIGN UP FUNCTION
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(), signUpRequest.getEmail(),
				signUpRequest.getMobile(), signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), false);

		Set<Role> roles = new HashSet<>();

		Role userRole = new Role(ERole.CUSTOMER);
		roles.add(userRole);

		user.setRoles(roles);
		try {
			userRepository.save(user);
		}
		catch(DataIntegrityViolationException ex) 
		{
			System.out.println("DataIntegrityViolationException");
		}
		
		sendMail(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@Async
	private void sendMail(User user) {
		try {
		    String token = UUID.randomUUID().toString();
		    creatMailVerificationToken(user, token);
		    mailSender.send(constructMailVerificationTokenEmail("http://localhost:7503/auth", token, user));
	    }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
	}
	
//RESET PASSWORD:
//-->
	//Reset Password that return Generic Response:
	@GetMapping("/user/resetPassword/{email}")
	public GenericResponse resetPassword(HttpServletRequest request, 
	  @PathVariable("email") String userEmail) throws UserNotFoundException {
		User user = userRepository.findByEmail(userEmail);
	    if (user == null) {
	        throw new UserNotFoundException("USER NOT FOUND");
	    }
	    try {
		    String token = UUID.randomUUID().toString();
		    createPasswordResetTokenForUser(user, token);
		    mailSender.send(constructResetTokenEmail("http://localhost:7503/auth", token, user));
	    }
        catch(Exception ex) {
        	ex.printStackTrace();
        }
	    return new GenericResponse("message.resetPasswordEmail");
	}
	
	
	//Function to Show change in password:	
	@GetMapping("/user/changePassword")
	public ModelAndView showChangePasswordPage(Locale locale, Model model, 
	  @RequestParam("token") String token) {
//	    String result = validatePasswordResetToken(token);
	    	ModelAndView mav = new ModelAndView();
	        mav.addObject("token", token);
	        mav.setViewName("changePass");
	        return mav;
	}
	
	//Function to process save password request:
	@GetMapping("/user/savePassword/{pass}/{token}")
	public GenericResponse savePassword(@PathVariable("pass") String pass,
										@PathVariable("token") String token) {
	 
		
	    String result = validatePasswordResetToken(token);
	 
	    if(result != null) {
	        return new GenericResponse("Invalid Token");
	    }
	 
	    User user = getUserByPasswordResetToken(token);
	    if(user != null) {
	        changeUserPassword(user, pass);
	        return new GenericResponse("Password Changed Successfully");
	    } else {
	        return new GenericResponse("Task Failed");
	    }
	}
	
	// Save Password to Database:
	private void changeUserPassword(User user, String newPassword) {
		user.setPassword(encoder.encode(newPassword));
		userRepository.save(user);
	}
	
	// Search Password reset token in database:
	private User getUserByPasswordResetToken(String token) {
		return passwordTokenRepository.findByToken(token).getUser();
	}
	
	// Generate and Save Password Reset Token:
	public void createPasswordResetTokenForUser(User user, String token) {
	    PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}
	
	//Token Mail Construction:
	private SimpleMailMessage constructResetTokenEmail(
			  String contextPath, String token, User user) {
			    String url = contextPath + "/user/changePassword?token=" + token;
			    String message = "Link to reset Password.\r\n"
			    				+ "Follow the mentioneed steps to reset password successfully:\n"
			    				+ "1. Copy the token: " + token +"\n"
			    				+ "2. Paste Exact token in the Token field.\n"
			    				+ "3. Enter New Password.\n"
			    				+ "4. Press Change Button";
			    return constructEmail("Reset Password", message + " \r\n" + url, user);
	}
	
	// Setting Mail Credentials:
	private SimpleMailMessage constructEmail(String subject, String body,
			User user) {
		SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject(subject);
	    email.setText(body);
		email.setTo("yagyesh03@gmail.com");
	    email.setFrom("yagyeshfms@gmail.com");
	    return email;
	}
	
	// Validate Password Reset Token:
	public String validatePasswordResetToken(String token) {
	    final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);	 
	    return !isTokenFound(passToken) ? "invalidToken" : null;
	}
	
	// Check if Password in DB or not:
	private boolean isTokenFound(PasswordResetToken passToken) {
	    return passToken != null;
	}
// <-- RESET PASSWORD
	
//-------------------------------------------//
	
// ACCOUNT MAIL ID VERIFICATION -->

	//Creat & Save Mail Verification Token:
	private void creatMailVerificationToken(User user, String token) {
		MailVerificationToken mvToken = new MailVerificationToken(token, user);
		mvtRepo.save(mvToken);
	}
	
	// Construct an e-Mail to send mail-verification-token:
	private SimpleMailMessage constructMailVerificationTokenEmail(
			  String contextPath, String token, User user) {
			    String url = contextPath + "/user/verifyMail?token=" + token;
			    String message = "Click on the given URL to VERIFY your EMAIL Account.";
			    return constructEmail("e-Mail Verification", message + " \r\n" + url, user);
	}

	// Verify Mail & Acivate Account:
	@GetMapping("/user/verifyMail")
	public ModelAndView verifyingMail(Locale locale, Model model,
			@RequestParam("token") String token) {
		MailVerificationToken mvt = validateVMToken(token);
		if(mvt.equals(null))
		{
			return null;
		}
		mvt.getUser().setMailVerfication(true);
		userRepository.save(mvt.getUser());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("MailVerificationCard");
		return mav;
	}	
	
	// Get VMToken From Database:
	public MailVerificationToken validateVMToken(String token) {
	    final MailVerificationToken passToken = mvtRepo.findByToken(token);	 
	    return !isTokenFound(passToken) ? null : passToken;
	}
		
	// Check if VMToken in DB or not:
	private boolean isTokenFound(MailVerificationToken passToken) {
	    return passToken != null;
	}
	
}