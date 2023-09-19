package com.example.lab3.controllers;

import com.example.lab3.models.*;
import com.example.lab3.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainController {

    private final UsersRepository repositoryUsers;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UsersRepository usersRepository){
        this.repositoryUsers = usersRepository;
    }

    @GetMapping("/regis")
    private String RegView()
    {
        return "regis";
    }
    @PostMapping("/regis")
    private String Reg(users user, Model model)
    {
        user.setActive(true);
        user.setRoles(Collections.singleton(roleEnum.USER));
        user.setPassword_User(passwordEncoder.encode(user.getPassword_User()));
        repositoryUsers.save(user);
        return "redirect:/login";
    }

    @GetMapping("/test")
    public String test(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        if(auth.getAuthorities().toArray()[0].toString().equals("USER")){

            return "redirect:/home";
        } else if (auth.getAuthorities().toArray()[0].toString().equals("ADMIN") || auth.getAuthorities().toArray()[0].toString().equals("EMPLOYEE")) {
            return "redirect:/employee/marks";
        }
        return "test";
    }

}
