/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sitesquad.ministore.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ACER
 */
@Entity
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    int productId;
    String name;
    int quantity;
    int productTypeId;
    double price;

    public Product() {
    }

    public Product(int productId, String name, int quantity, int productTypeId, double price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.productTypeId = productTypeId;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
