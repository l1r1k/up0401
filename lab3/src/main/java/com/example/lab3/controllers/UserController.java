package com.example.lab3.controllers;

import com.example.lab3.models.roleEnum;
import com.example.lab3.models.users;
import com.example.lab3.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/admin/user")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class UserController {

    private final UsersRepository repositoryUsers;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserController(UsersRepository repositoryUsers){
        this.repositoryUsers = repositoryUsers;;
    }

    @GetMapping("/users")
    public String users(@RequestParam(name= "login", defaultValue = "") String login, Model model){
        users getUser = new users();
        for(users item: repositoryUsers.findAll()){
            if(item.getLogin_User().equals(login)){
                getUser = item;
            }
        }
        model.addAttribute("getUser", getUser);
        model.addAttribute("login", login);
        model.addAttribute("users", repositoryUsers.findAll());
        model.addAttribute("newUser", new users());
        return "createuser";
    }
    @PostMapping("/searchUser")
    public String searchUser(@RequestParam(name = "login") String login, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("login", login);
        return "redirect:/admin/user/users";
    }

    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createUser(@ModelAttribute("newUser")@Valid users newUser, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/admin/user/users";
        }
        newUser.setRoles(Collections.singleton(roleEnum.USER));
        newUser.setPassword_User(passwordEncoder.encode(newUser.getPassword_User()));
        repositoryUsers.save(newUser);
        model.addAttribute("users", repositoryUsers.findAll());

        return "redirect:/admin/user/users";
    }

    @GetMapping("/users/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        users user = repositoryUsers.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID:" + id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return "editUsers";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable("id") long id,@ModelAttribute("user") @Valid users user,BindingResult result, Model model){
        if (result.hasErrors()) {
            return "editUsers";
        }
        user.setID_User(id);
        user.setPassword_User(passwordEncoder.encode(user.getPassword_User()));
        repositoryUsers.save(user);
        model.addAttribute("users", repositoryUsers.findAll());
        return "redirect:/admin/user/users";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        users user = new users();
        for(users item : repositoryUsers.findAll()){
            if(item.getLogin_User().equals(auth.getName())){
                user = item;
            }
        }
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return "editUsers";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") @Valid users user,BindingResult result, Model model){
        if (result.hasErrors()) {
            return "editUsers";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        users currentUser = new users();
        for(users item : repositoryUsers.findAll()){
            if(item.getLogin_User().equals(auth.getName())){
                currentUser = item;
            }
        }

        user.setID_User(currentUser.getID_User());
        user.setPassword_User(user.getPassword_User());
        repositoryUsers.save(user);
        model.addAttribute("user", user);
        return "redirect:/admin/user/profile";
    }

    @GetMapping("/delUser/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") long id,@RequestParam(name= "login", defaultValue = "") String login, @ModelAttribute("user")users user, Model model){
        users delUser = repositoryUsers.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: "+ id));
        repositoryUsers.delete(delUser);
        users getUser = new users();
        model.addAttribute("users", repositoryUsers.findAll());
        model.addAttribute("getUser", getUser);
        model.addAttribute("login", login);
        model.addAttribute("newUser", new users());
        return "createuser";
    }
}
