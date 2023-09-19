package com.example.lab3.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID_User;

    @NotBlank(message = "First name is required")
    @Size(max = 32, message = "Max Length 32")
    private String FirstName_User;

    @NotBlank(message = "Second name is required")
    @Size(max = 32, message = "Max Length 32")
    private String SecondName_User;

    @Size(max = 32, message = "Max Length 32")
    private String LastName_User;

    @NotBlank(message = "Login is required")
    @Size(max = 32, message = "Max Length 32")
    private String Login_User;

    @NotBlank(message = "Password is required")
    private String Password_User;

    private boolean active;

    @ElementCollection(targetClass = roleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<roleEnum> roles;


    public users(){}

    public users(String FirstName_User, String SecondName_User, String LastName_User, String Login_User, String Password_User, orders ownerOrder, boolean active, Set<roleEnum> roles){
        this.FirstName_User = FirstName_User;
        this.SecondName_User = SecondName_User;
        this.LastName_User = LastName_User;
        this.Login_User = Login_User;
        this.Password_User = Password_User;
        this.active = active;
        this.roles = roles;
    }

    public long getID_User() {
        return ID_User;
    }

    public void setID_User(long ID_User) {
        this.ID_User = ID_User;
    }

    public String getFirstName_User() {
        return FirstName_User;
    }

    public void setFirstName_User(String firstName_User) {
        FirstName_User = firstName_User;
    }

    public String getSecondName_User() {
        return SecondName_User;
    }

    public void setSecondName_User(String secondName_User) {
        SecondName_User = secondName_User;
    }

    public String getLastName_User() {
        return LastName_User;
    }

    public void setLastName_User(String lastName_User) {
        LastName_User = lastName_User;
    }

    public String getLogin_User() {
        return Login_User;
    }

    public void setLogin_User(String login_User) {
        Login_User = login_User;
    }

    public String getPassword_User() {
        return Password_User;
    }

    public void setPassword_User(String password_User) {
        Password_User = password_User;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<roleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<roleEnum> roles) {
        this.roles = roles;
    }
}
