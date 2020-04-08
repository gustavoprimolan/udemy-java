package com.barosanu.controller;

public class Singleton {

    private Singleton() {}

    private static Singleton instance = new Singleton();

    public static Singleton getInstace() {
        return instance;
    }

}
