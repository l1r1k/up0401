package com.example.lab3.controllers;

import com.example.lab3.models.roleEnum;
import com.example.lab3.models.users;
import com.example.lab3.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class RoleChangeController {


    private final UsersRepository userRepository;

    @Autowired
        public RoleChangeController(com.example.lab3.repositories.UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/updaterole")
    public String updView(Model model)
    {
        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("roles", roleEnum.values());
        return "roleChange";
    }

    @GetMapping("/updaterole/{id}")
    public String changeView(Model model, @PathVariable Long id)
    {
        model.addAttribute("user", userRepository.findById(id).orElseThrow());
        model.addAttribute("roles", roleEnum.values());
        return "updateRole";
    }


    @PostMapping("/updaterole/{id}")
    public String update_user(@RequestParam(name="roles[]", required = false) String[] roles,
                              @PathVariable Long id)
    {
        users user = userRepository.findById(id).orElseThrow();

        user.getRoles().clear();
        if(roles != null)
        {
            for(String role: roles)
            {
                user.getRoles().add(roleEnum.valueOf(role));
            }
        }

        userRepository.save(user);
        return "redirect:/employee/updaterole";
    }
}
