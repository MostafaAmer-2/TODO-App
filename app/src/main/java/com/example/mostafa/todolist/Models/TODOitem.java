package com.example.mostafa.todolist.Models;

public class TODOitem {
    public String title;

    public TODOitem (String name){
        title= name;
    }

    public String toString(){
        return this.title;
    }
}
