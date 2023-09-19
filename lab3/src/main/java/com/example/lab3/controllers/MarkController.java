package com.example.lab3.controllers;

import com.example.lab3.models.marks;
import com.example.lab3.models.shops;
import com.example.lab3.repositories.MarksRepository;
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
public class MarkController {

    private final MarksRepository marksRepository;

    @Autowired
    public MarkController(MarksRepository marksRepository){
        this.marksRepository = marksRepository;
    }

    @GetMapping("/marks")
    public String showMarks(@ModelAttribute("mark") marks mark, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("marks", marksRepository.findAll());
        return "marks";
    }
    @PostMapping(value = "/createNewMark")
    public String createMark(@Valid marks mark, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/employee/marks";
        }

        marksRepository.save(mark);
        model.addAttribute("marks", marksRepository.findAll());

        return "redirect:/employee/marks";
    }

    @GetMapping("/marks/{id}")
    public String editMark(Model model, @PathVariable("id") long id) {
        marks mark = marksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mark ID:" + id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("mark", mark);
        return "editMarks";
    }

    @PostMapping("/marks/{id}")
    public String updateMark(@PathVariable("id") long id,@ModelAttribute("mark") @Valid marks mark, BindingResult result, Model model){
        if (result.hasErrors()) {

            return "redirect:/employee/marks";
        }
        mark.setID_Mark(id);
        marksRepository.save(mark);
        model.addAttribute("marks", marksRepository.findAll());
        return "redirect:/employee/marks";
    }

    @GetMapping("/delMark/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteMark(@PathVariable("id") long id,@ModelAttribute("mark")marks mark, Model model){
        marks delMark = marksRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid mark ID: "+ id));
        marksRepository.delete(delMark);
        model.addAttribute("marks", marksRepository.findAll());
        return "marks";
    }
}
