package com.hashem.restdemo.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hashem.restdemo.model.Admin;
import com.hashem.restdemo.model.Student;
import com.hashem.restdemo.validation.IsValidPassword;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user" , uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    @Email(message = "This Email  is Not Valid You should Type  in  this case \'example@gmail.com\'")
    private String email;

    @Column(nullable = false)
    @IsValidPassword(message = "Your Password Should be greater than or equal 6 characters and have at least A-Z or a-z characters")
    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "userAdmin",cascade = CascadeType.ALL)
    private Admin admin;


    @JsonIgnore
    @OneToOne(mappedBy = "userStudent" , cascade = CascadeType.ALL)
    private Student student;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
