package com.example.lab3.controllers;

import com.example.lab3.DAO.cartDAO;
import com.example.lab3.models.cart;
import com.example.lab3.models.products;
import com.example.lab3.repositories.ProductsRepository;
import com.example.lab3.repositories.ShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/home")
@PreAuthorize("hasAnyAuthority('USER')")
public class HomeController {

    private final ProductsRepository repositoryProduct;
    private final cartDAO cartDAO;

    @Autowired
    public HomeController(cartDAO cartDAO, ProductsRepository repositoryProduct){
        this.cartDAO = cartDAO;
        this.repositoryProduct = repositoryProduct;
    }

    @GetMapping
    public String products(Model model){
        model.addAttribute("products", repositoryProduct.findAll());
        return "home";
    }
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") long id, Model model){

        products product = repositoryProduct.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: "+ id));
        cart cart = new cart(product, 1);
        cartDAO.save(cart);
        model.addAttribute("products", repositoryProduct.findAll());
        return "home";
    }
}
