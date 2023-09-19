package com.example.lab3.controllers;

import com.example.lab3.DAO.cartDAO;
import com.example.lab3.models.cart;
import com.example.lab3.models.orders;
import com.example.lab3.models.products;
import com.example.lab3.models.users;
import com.example.lab3.repositories.UsersRepository;
import com.example.lab3.repositories.ordersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('USER')")
public class CartController {

    private final cartDAO cartDAO;

    private final ordersRepository ordersRepository;

    private final UsersRepository usersRepository;

    @Autowired
    public CartController(cartDAO cartDAO, ordersRepository ordersRepository, UsersRepository usersRepository){
        this.cartDAO = cartDAO;
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/cart")
    public String cartShow(Model model){
        model.addAttribute("carts", cartDAO.index());
        return "cart";
    }

    @GetMapping("/delProductCart/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model){
        cartDAO.delete(id);
        model.addAttribute("carts", cartDAO.index());
        return "cart";
    }
    @GetMapping("/createOrder")
    public String createOrder(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Date date = new Date(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        orders order = new orders();
        String numberOrder = "";
        if(ordersRepository.findAll().isEmpty()){
            numberOrder = generateOrderNumber(1);
        }else{
            numberOrder = generateOrderNumber(ordersRepository.findAll().get(ordersRepository.findAll().size() - 1).getID_Order());
        }
        for(cart item : cartDAO.index()){
            order = new orders(numberOrder, date, false, item.getProduct());
            users user = new users();
            for(users element: usersRepository.findAll()){
                if(element.getLogin_User().equals(auth.getName())){
                    user = element;
                }
            }
            order.setUser(user);
            ordersRepository.save(order);
        }
        System.out.println("Роль: " + auth.getAuthorities());
        cartDAO.clearCarts();
        model.addAttribute("carts", cartDAO.index());
        return "cart";
    }

    public static String generateOrderNumber(long orderNumber) {
        String paddedOrderNumber = String.format("%08d", orderNumber);
        return paddedOrderNumber;
    }
}
