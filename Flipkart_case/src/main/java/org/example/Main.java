package org.example;

public class Main {
    static ShopNow shopNow=new ShopNow();
    public static void main(String[] args) {
        try {
            shopNow.butProduct("Chrome");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}