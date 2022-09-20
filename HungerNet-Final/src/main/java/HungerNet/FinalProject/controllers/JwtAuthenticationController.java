package HungerNet.FinalProject.controllers;


import HungerNet.FinalProject.configurations.jwtconfig.payload.JwtRequest;
import HungerNet.FinalProject.configurations.jwtconfig.payload.JwtResponse;
import HungerNet.FinalProject.configurations.jwtconfig.JwtTokenUtil;
import HungerNet.FinalProject.model.impl.UserDetailsImpl;
import HungerNet.FinalProject.configurations.jwtconfig.payload.MessageResponse;
import HungerNet.FinalProject.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authenticate")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtUtils;

    @Autowired
    private UserServiceImpl userDetailsService;
    private static final Logger logger = LogManager.getLogger(ItemController.class);

    //login request
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody JwtRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //generate token
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            logger.info(userDetails.getUsername() + " is logged in!");
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getUsername(),
                    roles));
        } catch (Exception exception) {
            return ResponseEntity.ok(new MessageResponse(exception.getMessage()));
        }
    }

}