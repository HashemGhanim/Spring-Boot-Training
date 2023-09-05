package com.hashem.restdemo.security.auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest){
       return authenticationService.authenticate(authenticationRequest);
    }


}
