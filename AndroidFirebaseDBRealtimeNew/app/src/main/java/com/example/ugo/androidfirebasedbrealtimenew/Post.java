package com.example.ugo.androidfirebasedbrealtimenew;

public class Post {
    private String title,content;

    public Post(){


    }
    public Post(String title,String content){
        this.title = title;
        this.content = content;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CharSequence getContent() {
        return content;
    }
}
