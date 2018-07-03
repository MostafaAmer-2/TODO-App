package com.example.mostafa.todolist.Interfaces;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public interface MainView {

     void displayItemAdded();
     void displayItemDeleted();
     void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
