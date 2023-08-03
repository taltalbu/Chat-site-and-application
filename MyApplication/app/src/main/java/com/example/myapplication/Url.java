package com.example.myapplication;


public class Url {
    private static Url instance;
    private String url;

    private Url() {
        // Private constructor to prevent external instantiation
    }

    public static synchronized Url getInstance() {
        if (instance == null) {
            instance = new Url();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}