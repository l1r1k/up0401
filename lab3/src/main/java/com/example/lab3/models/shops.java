package com.example.lab3.models;

import javax.naming.Name;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "shops")
public class shops {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID_Shop;

    @NotBlank(message = "Shop name is required")
    private String Name_Shop;

    @NotBlank(message = "Shop address is required")
    private String Address_Shop;

    public shops(){}

    public shops(String Address_Shop, String Name_Shop){
        this.Address_Shop = Address_Shop;
        this.Name_Shop = Name_Shop;
    }

    public long getID_Shop() {
        return ID_Shop;
    }

    public void setID_Shop(long ID_Shop) {
        this.ID_Shop = ID_Shop;
    }

    public String getAddress_Shop() {
        return Address_Shop;
    }

    public void setAddress_Shop(String address_Shop) {
        Address_Shop = address_Shop;
    }

    public String getName_Shop() {
        return Name_Shop;
    }

    public void setName_Shop(String name_Shop) {
        Name_Shop = name_Shop;
    }
}
