package com.example.toylanguage_intellij.Controller;

public class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }

    public void printMessage() {
        System.out.println(getMessage());
    }
}
