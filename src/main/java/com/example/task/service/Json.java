package com.example.task.service;


import org.json.JSONObject;

public class Json {
    public static void main(String[] args) {
        String myString = new JSONObject().put("JSON", "Hello, World!").toString();
        System.out.println(myString);
    }
}
