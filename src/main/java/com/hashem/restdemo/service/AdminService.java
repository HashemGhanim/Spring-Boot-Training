package com.hashem.restdemo.service;

import com.hashem.restdemo.exception.BadRequestException;
import com.hashem.restdemo.model.Admin;
import com.hashem.restdemo.repository.AdminRepo;
import com.hashem.restdemo.repository.UserRepo;
import com.hashem.restdemo.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;


@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private UserRepo userRepo;

    @Cacheable(value = "getAdmins" , key = "#root.methodName")
    public List<Admin> getAdmins(){
        return adminRepo.findAll();
    }

    @Cacheable(value = "getAdmin" , key = "#adminId")
    public Admin getAdmin(int adminId) {
        return adminRepo.findById(adminId).get();
    }

    @Transactional
    @CacheEvict(value = {"getAdmins" , "getAdmin"} , key = "#admin.adminId" , allEntries = true)
    public Admin setAdmin(@Valid Admin admin, @Valid User user) {
        User isUserInfoAlreadyExist = userRepo.findByEmail(user.getEmail()).orElse(null);

        if(isUserInfoAlreadyExist != null){
            throw new BadRequestException("This Email is already exist, Please Try again .. !");
        }

        Admin adminSaved = adminRepo.save(admin);
        userRepo.save(user);

        return adminSaved;
    }

    @CacheEvict(value = {"getAdmins" , "getAdmin"} , key = "#adminId" , allEntries = true)
    public void deleteAdmin(int adminId) {
        adminRepo.deleteById(adminId);
    }


    @Transactional
    @CacheEvict(value = {"getAdmins" , "getAdmin"} , key = "#adminId" , allEntries = true)
    public Admin updateAdmin(@Valid Admin admin,@Valid User user, int adminId) {

        Admin mainAdmin = adminRepo.findById(adminId).orElse(null);

        if(mainAdmin == null){
            throw new BadRequestException("This Admin is already not exist please try again ... !");
        }

        User isUserInfoAlreadyExist = userRepo.findByEmail(user.getEmail()).orElse(null);

        if(isUserInfoAlreadyExist != null && !mainAdmin.getUserAdmin().getEmail().equals(user.getEmail())){
            throw new BadRequestException("This Email is already exist, Please Try again .. !");
        }


        mainAdmin.setFirstName(admin.getFirstName());
        mainAdmin.setLastName(admin.getLastName());
        mainAdmin.setPhone(admin.getPhone());


        User userForAdmin = mainAdmin.getUserAdmin();
        userForAdmin.setEmail(user.getEmail());
        userForAdmin.setPassword(user.getPassword());

        mainAdmin.setUserAdmin(userForAdmin);

        userRepo.save(userForAdmin);

        return adminRepo.save(mainAdmin);
    }
}
