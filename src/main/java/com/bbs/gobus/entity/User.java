package com.bbs.gobus.entity;

import com.bbs.gobus.dto.Role;
import com.bbs.gobus.dto.UserDto;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false,unique = true)
    private String password;
     @Column(nullable = false,unique = true)
    private String email;
     @Column(nullable = false,unique = true)
    private Long mobile;
    @Enumerated(EnumType.STRING)
    private Role role;

    
 public User(UserDto dto) {
    this.username = dto.getName();
    this.email = dto.getEmail();
    this.mobile = dto.getMobile();
    this.role =Role.valueOf(dto.getRole());
    this.password =AES.encrypt(dto.getPassword());
    
    }
    
}
