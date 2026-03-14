package org.skypro.skyshop.basket;

import org.skypro.skyshop.product.Product;

import java.util.*;

public class ProductBasket {
    private Map<String, List<Product>> productsMap;

    public ProductBasket() {
        this.productsMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        String productName = product.getProductName();
        productsMap.computeIfAbsent(productName, k -> new ArrayList<>()).add(product);
    }

    public int getTotalPrice() {
        int total = 0;
        for (List<Product> productList : productsMap.values()) {
            for (Product product : productList) {
                total += product.getPriceOfProduct();
            }
        }
        return total;
    }

    public void printContents() {
        if (productsMap.isEmpty()) {
            System.out.println("в корзине пусто");
            return;
        }

        for (List<Product> productList : productsMap.values()) {
            for (Product product : productList) {
                System.out.println(product.toString());
            }
        }

        int specialCount = 0;
        for (List<Product> productList : productsMap.values()) {
            for (Product product : productList) {
                if (product.isSpecial()) {
                    specialCount++;
                }
            }
        }
        System.out.println("Итого: " + getTotalPrice());
        System.out.println("Специальных товаров: " + specialCount);
    }

    public boolean containsProduct(String productName) {
        return productsMap.containsKey(productName);
    }

    public void clearBasket() {
        productsMap.clear();
    }

    public List<Product> removeProductsByName(String name) {
        List<Product> removedProducts = new ArrayList<>();
        List<Product> productsToRemove = productsMap.remove(name);
        if (productsToRemove != null) {
            removedProducts.addAll(productsToRemove);
        }

        return removedProducts;
    }

    public int getUniqueProductCount() {
        return productsMap.size();
    }

    public int getTotalProductCount() {
        int count = 0;
        for (List<Product> productList : productsMap.values()) {
            count += productList.size();
        }
        return count;
    }
}