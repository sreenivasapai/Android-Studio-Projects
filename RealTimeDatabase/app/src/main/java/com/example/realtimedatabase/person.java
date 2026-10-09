package com.example.realtimedatabase;

public class person {
    private String name;
    private String number;

    //1. Required: public no argument constructor
    public person(){

    }
    //2. optional: Loaded Constructor for convenience
    public person(String name,String number){
        this.name=name;
        this.number=number;
    }
    //3,Getters and setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;

    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

