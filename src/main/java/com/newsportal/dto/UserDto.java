package com.newsportal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDto {
    private Long id;
    private String email;
    private String userName;
    private LocalDate dateOfBirth;
    private String gender;
    private LocalDateTime registrationDate;
    private String interests;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }
}
