package com.example.lab3.controllers;

import com.example.lab3.models.shops;
import com.example.lab3.repositories.ShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
public class ShopController {

    private final ShopsRepository repositoryShops;

    @Autowired
    public ShopController(ShopsRepository repositoryShops){
        this.repositoryShops = repositoryShops;
    }

    @GetMapping("/shops")
    public String showShops(@ModelAttribute("shop") shops shop, Model model){
        model.addAttribute("shops", repositoryShops.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        return "shops";
    }
    @PostMapping(value = "/createNewShop")
    public String createShop(@Valid shops shop, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/employee/shops";
        }

        repositoryShops.save(shop);
        model.addAttribute("shops", repositoryShops.findAll());

        return "redirect:/employee/shops";
    }

    @GetMapping("/shops/{id}")
    public String editShop(Model model, @PathVariable("id") long id) {
        shops shop = repositoryShops.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid shop ID:" + id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("shop", shop);
        return "editShops";
    }

    @PostMapping("/shops/{id}")
    public String updateShop(@PathVariable("id") long id,@ModelAttribute("shop") @Valid shops shop, BindingResult result, Model model){
        if (result.hasErrors()) {

            return "redirect:/employee/shops";
        }
        shop.setID_Shop(id);
        repositoryShops.save(shop);
        model.addAttribute("shops", repositoryShops.findAll());
        return "redirect:/employee/shops";
    }

    @GetMapping("/delShop/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteShop(@PathVariable("id") long id,@ModelAttribute("shop")shops shop, Model model){
        shops delShop = repositoryShops.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid post ID: "+ id));
        repositoryShops.delete(delShop);
        model.addAttribute("shops", repositoryShops.findAll());
        return "shops";
    }
}
