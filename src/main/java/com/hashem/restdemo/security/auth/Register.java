package com.hashem.restdemo.security.auth;

import com.hashem.restdemo.security.model.Role;
import com.hashem.restdemo.validation.IsValidName;
import com.hashem.restdemo.validation.IsValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Register {

    @NotNull(message = "First Name Of Student Should Be Not Null")
    @NotEmpty(message = "First Name Of Student Should Be Not Empty")
    @IsValidName(message = "First Name Should be initials with characters a-z Or A-Z")
    private String firstName;

    @IsValidName(message = "Last Name Should be initials with characters a-z Or A-Z")
    private String lastName;

    @NotNull(message = "Phone Should Be Not Null")
    @NotEmpty(message = "Phone Should Be Not Empty")
    private String Phone;

    @Email(message = "This Email  is Not Valid You should Type  in  this case \'example@gmail.com\'")
    private String email;

    @IsValidPassword(message = "Your Password Should be greater than or equal 6 characters and have at least A-Z or a-z characters")
    private String password;

    private Role role;
}
