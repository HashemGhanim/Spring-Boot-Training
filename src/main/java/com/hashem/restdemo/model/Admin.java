package com.hashem.restdemo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashem.restdemo.security.model.User;
import com.hashem.restdemo.validation.IsValidName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    @NotNull(message = "First Name Of Student Should Be Not Null")
    @NotEmpty(message = "First Name Of Student Should Be Not Empty")
    @IsValidName(message = "First Name Should be initials with characters a-z Or A-Z")
    private String firstName;

    @IsValidName(message = "Last Name Should be initials with characters a-z Or A-Z")
    private String lastName;

    @NotNull(message = "Phone Should Be Not Null")
    @NotEmpty(message = "Phone Should Be Not Empty")
    private String phone;



    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userAdmin;



}
