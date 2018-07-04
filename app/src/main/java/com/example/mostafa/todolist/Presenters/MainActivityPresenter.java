package com.example.mostafa.todolist.Presenters;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.mostafa.todolist.Activities.MainActivity;
import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Interfaces.MainView;
import com.example.mostafa.todolist.Models.TODOitem;
import com.example.mostafa.todolist.Network;
import com.example.mostafa.todolist.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivityPresenter implements MainPresenter {

    private ArrayList<TODOitem> items = new ArrayList<TODOitem>();
    private ArrayAdapter<TODOitem> adapter;

    private MainView mainView;
    private Network network;
    private Context ctx;

    public MainActivityPresenter(MainView view, Context ctx) {
        mainView = view;
        this.ctx = ctx;
        adapter = new ArrayAdapter<TODOitem>(ctx, R.layout.custom_list_item, items);
        network = new Network(this);
    }

    @Override
    public void onAddBtnClicked() {
        String itemEntered = ((MainActivity) mainView).getItemET();
        ((MainActivity) mainView).setItemET("");
        TODOitem newItem = new TODOitem(itemEntered);
        network.addItem(newItem);

    }

    @Override
    public void onItemClicked(int position) {
        TODOitem itemToBeRemoved = items.get(position); //get the position of the item the user wishes to remove
        network.remove(itemToBeRemoved);//remove value from database
        items.remove(position); //remove value from the item list
        notifyAdapter();

    }

    @Override
    public void onLogoutBtnClicked() {
        network.signOut();
    }

    public ArrayList<TODOitem> getItems() {
        return items;
    }

    public void notifyAdapter() {
        adapter.notifyDataSetChanged();
    }

    public ArrayAdapter<TODOitem> getAdapter() {
        return adapter;
    }

    public void removeItem(TODOitem item) {
        for (int i=0;i<items.size();i++) {
           if(items.get(i).title.equals(item.title))
               items.remove(i);

        }
        notifyAdapter();
    }

    public void addItem(TODOitem item) {
        items.add(item);
        notifyAdapter();
    }

}
