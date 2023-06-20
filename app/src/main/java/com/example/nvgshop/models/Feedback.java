package com.example.nvgshop.models;

public class Feedback {
    private int id;
    private String name;
    private String status;
    private String content;

    public Feedback(int id, String name, String status, String content) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }
}

