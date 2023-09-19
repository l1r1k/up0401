package com.example.lab3.controllers;

import com.example.lab3.models.marks;
import com.example.lab3.models.models;
import com.example.lab3.repositories.ModelsRepository;
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
@PreAuthorize("hasAnyAuthority('EMPLOYEE' , 'ADMIN')")
public class ModelController {

    private final ModelsRepository modelsRepository;

    @Autowired
    public ModelController(ModelsRepository modelsRepository){
        this.modelsRepository = modelsRepository;
    }

    @GetMapping("/models")
    public String showModels(@ModelAttribute("model")models model, Model viewModel){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        viewModel.addAttribute("role", role);
        viewModel.addAttribute("models", modelsRepository.findAll());
        return "models";
    }
    @PostMapping(value = "/createNewModel")
    public String createModel(@Valid models model, BindingResult result, Model viewModel){
        if(result.hasErrors()){
            return "redirect:/employee/models";
        }

        modelsRepository.save(model);
        viewModel.addAttribute("models", modelsRepository.findAll());

        return "redirect:/employee/models";
    }

    @GetMapping("/models/{id}")
    public String editModel(Model viewModel, @PathVariable("id") long id) {
        models model = modelsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid model ID:" + id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        viewModel.addAttribute("role", role);
        viewModel.addAttribute("model", model);
        return "editModels";
    }

    @PostMapping("/models/{id}")
    public String updateModel(@PathVariable("id") long id,@ModelAttribute("model") @Valid models model, BindingResult result, Model viewModel){
        if (result.hasErrors()) {

            return "redirect:/employee/models";
        }
        model.setID_Model(id);
        modelsRepository.save(model);
        viewModel.addAttribute("models", modelsRepository.findAll());
        return "redirect:/employee/models";
    }

    @GetMapping("/delModel/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteModel(@PathVariable("id") long id,@ModelAttribute("model")models model, Model viewModel){
        models delModel = modelsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid model ID: "+ id));
        modelsRepository.delete(delModel);
        viewModel.addAttribute("models", modelsRepository.findAll());
        return "models";
    }
}
