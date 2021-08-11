package com.noteapp;

public class FirebaseModel {

    private String title; // same name define in fireStore documents
    private String content;

    public FirebaseModel() {
    }

    public String getTitle() {
        return title;
    }

    public FirebaseModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

