package com.example.lab3.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "products")
public class products {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID_Product;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = marks.class)
    @JoinColumn(name = "mark_id")
    private marks mark;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = models.class)
    @JoinColumn(name = "model_id")
    private models model;

    @NotNull(message = "Count product is required")
    private int Count_Product;

    @NotNull(message = "Cost product is required")
    private double Cost_Product;

    @NotBlank(message = "Article product is required")
    @Size(min = 8, max = 8, message = "Min length 8")
    private String Article_Product;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = shops.class)
    @JoinColumn(name = "shop_id")
    private shops shop;

    public products(){}

    public products(String Name_Product, int Count_Product, double Cost_Product, String Article_Product){
        this.Count_Product = Count_Product;
        this.Cost_Product = Cost_Product;
        this.Article_Product = Article_Product;
    }

    public long getID_Product() {
        return ID_Product;
    }

    public void setID_Product(long ID_Product) {
        this.ID_Product = ID_Product;
    }

    public int getCount_Product() {
        return Count_Product;
    }

    public void setCount_Product(int count_Product) {
        Count_Product = count_Product;
    }

    public double getCost_Product() {
        return Cost_Product;
    }

    public void setCost_Product(double cost_Product) {
        Cost_Product = cost_Product;
    }

    public String getArticle_Product() {
        return Article_Product;
    }

    public void setArticle_Product(String article_Product) {
        Article_Product = article_Product;
    }

    public marks getMark() {
        return mark;
    }

    public void setMark(marks mark) {
        this.mark = mark;
    }

    public models getModel() {
        return model;
    }

    public void setModel(models model) {
        this.model = model;
    }

    public shops getShop() {
        return shop;
    }

    public void setShop(shops shop) {
        this.shop = shop;
    }
}
