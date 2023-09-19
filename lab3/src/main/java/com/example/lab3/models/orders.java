package com.example.lab3.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "orders")
public class orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID_Order;

    @NotBlank(message = "Number order is ruquired")
    private String Number_Order;

    private Date Date_Order;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = users.class)
    @JoinColumn(name = "user_id")
    private users user;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = products.class)
    @JoinColumn(name = "product_id")
    private products product;

    private boolean Status_Order;

    public orders(){}

    public orders(String Number_Order, Date Date_Order, boolean Status_Order, products product){
        this.Number_Order = Number_Order;
        this.Date_Order = Date_Order;
        this.Status_Order = Status_Order;
        this.product = product;
    }


    public long getID_Order() {
        return ID_Order;
    }

    public void setID_Order(long ID_Order) {
        this.ID_Order = ID_Order;
    }

    public String getNumber_Order() {
        return Number_Order;
    }

    public void setNumber_Order(String number_Order) {
        Number_Order = number_Order;
    }

    public Date getDate_Order() {
        return Date_Order;
    }

    public void setDate_Order(Date date_Order) {
        Date_Order = date_Order;
    }

    public users getUser() {
        return user;
    }

    public void setUser(users user) {
        this.user = user;
    }

    public boolean getStatus_Order() {
        return Status_Order;
    }

    public void setStatus_Order(boolean status_Order) {
        Status_Order = status_Order;
    }

    public products getProduct() {
        return product;
    }

    public void setProduct(products product) {
        this.product = product;
    }
}
