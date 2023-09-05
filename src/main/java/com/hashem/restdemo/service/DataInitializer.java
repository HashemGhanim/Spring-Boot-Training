package com.hashem.restdemo.service;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.model.Admin;
import com.hashem.restdemo.repository.AdminRepo;
import com.hashem.restdemo.repository.UserRepo;
import com.hashem.restdemo.security.model.Role;
import com.hashem.restdemo.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private UserRepo userRepo ;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        try{
            User user = User.builder().email("admin@gmail.com").password(passwordEncoder.encode("admin")).role(Role.ADMIN).build();
            Admin admin = Admin.builder()
                    .userAdmin(user)
                    .firstName("Hashem")
                    .lastName("Zerei")
                    .phone("0569922586")
                    .build();

            User isUserInfoAlreadyExist = userRepo.findByEmail(user.getEmail()).orElse(null);

            if(isUserInfoAlreadyExist != null){
                throw new BadRequestException("");
            }

            Admin adminSaved = adminRepo.save(admin);
            userRepo.save(user);
        }catch (BadRequestException ex){
            System.out.println(ex.getMessage());
        }
    }
}
