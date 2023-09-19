package com.example.lab3.DAO;

import com.example.lab3.models.cart;
import com.example.lab3.models.products;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class cartDAO {

    private static int CART_ID;

    private final ArrayList<cart> carts = new ArrayList<cart>();

    public ArrayList<cart> index(){
        return carts;
    }

    public void save(cart product){
        product.setId(++CART_ID);
        carts.add(product);
    }

    public cart show(int id) {
        return carts.stream().filter(cart -> cart.getId() == id).findAny().orElse(null);
    }

    public void delete(int id){
        carts.removeIf(p-> p.getId() == id);
    }

    public void clearCarts(){
        this.carts.clear();
    }

    public static int getCartId(){
        return CART_ID;
    }

    public static void setCartId(int cartId){
        CART_ID = cartId;
    }
}
