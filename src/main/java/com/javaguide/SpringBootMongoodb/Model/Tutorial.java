package com.javaguide.SpringBootMongoodb.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tutorials")
public class Tutorial {
    @Id
    private  String id;
    private String title;
    private String description;
    private boolean published;

    //khoi tao khong tham so:
    public Tutorial(){}

    //Ham khoi tao co tham so:
    public Tutorial(String title, String description, boolean published){
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public String getId(){
        return id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String  getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String  getDescription(){
        return description;
    }

    public void setPublished(boolean published){
        this.published = published;
    }

    public boolean getPublished(){
        return published;
    }

    @Override
    public String toString(){
        return "Tutorial [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
    }
}

