package com.hashem.restdemo.controller;

import com.hashem.restdemo.model.Admin;
import com.hashem.restdemo.security.auth.Register;
import com.hashem.restdemo.security.model.Role;
import com.hashem.restdemo.security.model.User;
import com.hashem.restdemo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Admin> getAdmins(){
        return adminService.getAdmins();
    }

    @GetMapping("/{adminId}")
    public Admin getAdmin(@PathVariable int adminId){
        return adminService.getAdmin(adminId);
    }


    @PostMapping
    public Admin setAdmin(@RequestBody @Valid Register register){
        User user = User.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(Role.ADMIN)
                .build();

        Admin admin = Admin.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .userAdmin(user)
                .build();

        return adminService.setAdmin(admin , user);
    }

    @DeleteMapping("/{adminId}")
    public void deleteAdmin(@PathVariable int adminId){
       adminService.deleteAdmin(adminId);
    }

    @PutMapping("/{adminId}")
    public Admin updateAdmin(@RequestBody Register register , @PathVariable int adminId){
        User user = User.builder()
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(Role.ADMIN)
                .build();

        Admin admin = Admin.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .phone(register.getPhone())
                .userAdmin(user)
                .build();
        return adminService.updateAdmin(admin , user , adminId);
    }
}
