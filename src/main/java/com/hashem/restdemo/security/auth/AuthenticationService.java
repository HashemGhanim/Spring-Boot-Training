package com.hashem.restdemo.security.auth;



import com.hashem.restdemo.repository.UserRepo;
import com.hashem.restdemo.security.model.User;
import com.hashem.restdemo.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        User user = userRepo.findByEmail(authenticationRequest.getEmail()).orElse(null);

        if(user == null){
            throw new UsernameNotFoundException("User Not Found Please Try Again !");
        }

        String jwtToken;
        Map<String , Object> extraClaims = new HashMap<>();
        extraClaims.put("role" , user.getRole());
        jwtToken = jwtService.generateToken((HashMap<String, Object>) extraClaims, user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

