package com.example.lab3.controllers;

import com.example.lab3.models.orders;
import com.example.lab3.models.users;
import com.example.lab3.repositories.ProductsRepository;
import com.example.lab3.repositories.UsersRepository;
import com.example.lab3.repositories.ordersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/employee")
@PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN', 'USER')")
public class OrderController {
    private final ordersRepository repositoryOrders;

    private final UsersRepository repositoryUsers;

    @Autowired
    public OrderController(ordersRepository repositoryOrders, UsersRepository repositoryUsers){
        this.repositoryOrders = repositoryOrders;
        this.repositoryUsers = repositoryUsers;
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public String orders(@ModelAttribute("order") orders order, Model model){
        Iterable<users> users = repositoryUsers.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("users", users);
        model.addAttribute("orders", repositoryOrders.findAll());
        return "orders";
    }


    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public String editOrder(Model model, @PathVariable("id") long id) {
        orders order = repositoryOrders.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order ID:" + id));
        Iterable<users> users = repositoryUsers.findAll();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();
        model.addAttribute("role", role);
        model.addAttribute("users", users);
        model.addAttribute("order", order);
        return "editOrders";
    }

    @PostMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public String updateOrder(@PathVariable("id") long id,@ModelAttribute("order") @Valid orders order, @RequestParam String login, BindingResult result, Model model){
        if (result.hasErrors()) {
            Iterable<users> users = repositoryUsers.findAll();
            model.addAttribute("users", users);
            return "redirect:/user/employee/orders";
        }
        users getUser = new users();
        for(users element: repositoryUsers.findAll()){
            if(element.getLogin_User().equals(login)){
                getUser = element;
            }
        }
        order.setID_Order(id);
        order.setUser(getUser);
        repositoryOrders.save(order);
        model.addAttribute("orders", repositoryOrders.findAll());
        return "redirect:/user/employee/orders";
    }

    @GetMapping("/delOrder/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteOrder(@PathVariable("id") long id,@ModelAttribute("order")orders order, Model model){
        orders neworder = repositoryOrders.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order ID: "+ id));
        repositoryOrders.delete(neworder);
        model.addAttribute("orders", repositoryOrders.findAll());
        return "orders";
    }

    @GetMapping("/userOrders")
    public String userOrdersShow(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        users user = new users();
        for(users item : repositoryUsers.findAll()){
            if(item.getLogin_User().equals(auth.getName())){
                user = item;
            }
        }
        List<orders> userOrders = new ArrayList<orders>();
        for(orders element: repositoryOrders.findAll()){
            if(element.getUser().equals(user)){
                userOrders.add(element);
            }
        }
        model.addAttribute("orders", userOrders);
        return "userOrders";
    }
}
