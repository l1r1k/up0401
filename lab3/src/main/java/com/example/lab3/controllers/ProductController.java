package com.example.lab3.controllers;

import com.example.lab3.models.marks;
import com.example.lab3.models.models;
import com.example.lab3.models.products;
import com.example.lab3.models.shops;
import com.example.lab3.repositories.MarksRepository;
import com.example.lab3.repositories.ModelsRepository;
import com.example.lab3.repositories.ProductsRepository;
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
public class ProductController {

    private final ProductsRepository repositoryProducts;

    private final MarksRepository marksRepository;

    private final ModelsRepository modelsRepository;

    private final ShopsRepository shopsRepository;

    @Autowired
    public ProductController(ProductsRepository repositoryProducts, MarksRepository marksRepository, ModelsRepository modelsRepository, ShopsRepository shopsRepository){
        this.repositoryProducts = repositoryProducts;
        this.marksRepository = marksRepository;
        this.modelsRepository = modelsRepository;
        this.shopsRepository = shopsRepository;
    }

    @GetMapping("/products")
    public String products(@ModelAttribute("product") products product, Model model){
        Iterable<marks> marks = marksRepository.findAll();
        Iterable<models> models = modelsRepository.findAll();
        Iterable<shops> shops = shopsRepository.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("products", repositoryProducts.findAll());
        model.addAttribute("marks", marks);
        model.addAttribute("models", models);
        model.addAttribute("shops", shops);
        return "products";
    }
    @PostMapping(value = "/createNewProduct")
    public String createProduct(@RequestParam Long mark, @RequestParam Long model, @RequestParam Long shop, @Valid products product, BindingResult result, Model viewModel){
        if(result.hasErrors()){
            return "redirect:/employee/products";
        }
        marks mark2 = marksRepository.findById(mark).orElseThrow();
        models model2 = modelsRepository.findById(model).orElseThrow();
        shops shop2 = shopsRepository.findById(shop).orElseThrow();

        product.setMark(mark2);
        product.setModel(model2);
        product.setShop(shop2);

        repositoryProducts.save(product);
        Iterable<marks> marks = marksRepository.findAll();
        Iterable<models> models = modelsRepository.findAll();
        Iterable<shops> shops = shopsRepository.findAll();
        viewModel.addAttribute("products", repositoryProducts.findAll());
        viewModel.addAttribute("marks", marks);
        viewModel.addAttribute("models", models);
        viewModel.addAttribute("shops", shops);

        return "redirect:/employee/products";
    }

    @GetMapping("/products/{id}")
    public String editProduct(Model model, @PathVariable("id") long id) {
        products product = repositoryProducts.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID:" + id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("product", product);
        return "editProducts";
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable("id") long id, @RequestParam Long mark, @RequestParam Long model, @RequestParam Long shop, @ModelAttribute("product") @Valid products product, BindingResult result, Model viewModel){
        if (result.hasErrors()) {
            return "redirect:/employee/products";
        }
        marks mark2 = marksRepository.findById(mark).orElseThrow();
        models model2 = modelsRepository.findById(model).orElseThrow();
        shops shop2 = shopsRepository.findById(shop).orElseThrow();

        product.setID_Product(id);
        product.setMark(mark2);
        product.setModel(model2);
        product.setShop(shop2);

        repositoryProducts.save(product);
        Iterable<marks> marks = marksRepository.findAll();
        Iterable<models> models = modelsRepository.findAll();
        Iterable<shops> shops = shopsRepository.findAll();
        viewModel.addAttribute("products", repositoryProducts.findAll());
        viewModel.addAttribute("marks", marks);
        viewModel.addAttribute("models", models);
        viewModel.addAttribute("shops", shops);
        return "redirect:/employee/products";
    }

    @GetMapping("/delProduct/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteProduct(@PathVariable("id") long id,@ModelAttribute("product")products product, Model model){
        products newproduct = repositoryProducts.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: "+ id));
        repositoryProducts.delete(newproduct);
        Iterable<marks> marks = marksRepository.findAll();
        Iterable<models> models = modelsRepository.findAll();
        Iterable<shops> shops = shopsRepository.findAll();
        model.addAttribute("products", repositoryProducts.findAll());
        model.addAttribute("marks", marks);
        model.addAttribute("models", models);
        model.addAttribute("shops", shops);
        return "products";
    }
}
