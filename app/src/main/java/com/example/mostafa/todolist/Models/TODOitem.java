package com.example.mostafa.todolist.Models;

import com.google.firebase.database.DataSnapshot;

public class TODOitem {
    public String title;

    public TODOitem(String name) {
        title = name;
    }

    public String toString() {
        return this.title;
    }

    public static TODOitem convertToItem(DataSnapshot snapshot) {
        return new TODOitem(snapshot.getKey());
    }
}
