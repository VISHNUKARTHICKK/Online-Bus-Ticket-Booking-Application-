package com.bbs.gobus.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
@Size(min=3,max=20,message = "*Name should be between 3 to 20 characters")
 private String name;
 @Email(message = "*Email should be valid")
 @NotEmpty(message = " *Email is required")
 private String email;
@DecimalMin(value="6000000000",message = "*Mobile number should be valid")
@DecimalMax(value="9999999999",message = "*Mobile number should be valid")
@NotNull(message = "*Mobile number is required")
 private Long mobile;
@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "*Password must contain at least 6 characters, including uppercase, lowercase letters, and numbers")
 private String password;
@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "*Password must contain at least 6 characters, including uppercase, lowercase letters, and numbers")
 private String confirmPassword;
@NotEmpty(message = "* Role is required")
 private String role;
 @AssertTrue(message = "*You must accept the terms and conditions")
 private boolean terms;

}
