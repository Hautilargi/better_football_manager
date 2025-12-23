package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import  com.hautilargi.footman.users.repository.UserRepository;
import com.hautilargi.footman.services.UserManagementService;
import com.hautilargi.footman.users.model.LoginRequest;
import com.hautilargi.footman.users.model.RegistrationRequest;
import com.hautilargi.footman.users.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserManagementService ums;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
                            System.out.println("Login start! "+request.toString());

        User user = userRepository.findByName(request.username())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
                        System.out.println("Password wrong!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // Session erzeugen
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("USER_ID", user.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegistrationRequest request,
            HttpServletRequest httpRequest) {
        try{
                        System.out.println("Registraion start "+request.toString());

            User user = ums.registerUser(request.username(),request.email(), request.password(), request.teamname());
            // Session erzeugen
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute("USER_ID", user.getId());
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            System.err.println(e);
            return ResponseEntity.internalServerError().build();
        }



    }


@GetMapping("/api/me")
public User me(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("USER_ID") == null) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    Long userId = (Long) session.getAttribute("USER_ID");
    return userRepository.findById(userId).get();
}


@PostMapping("/logout")
public void logout(HttpServletRequest request) {
    request.getSession(false).invalidate();
}
}
