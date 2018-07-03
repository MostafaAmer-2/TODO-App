package com.example.mostafa.todolist.Presenters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.mostafa.todolist.Activities.MainActivity;
import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Interfaces.MainView;
import com.example.mostafa.todolist.Models.TODOitem;
import com.example.mostafa.todolist.Network;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivityPresenter implements MainPresenter {

    private ArrayList<TODOitem> items=new ArrayList<TODOitem>();
    private ArrayAdapter<TODOitem> adapter;

    private MainView mainView;
    private Network network;
    private Context ctx;
    // private MainInteractor mainInteractor;

    public MainActivityPresenter(MainView view, Context ctx){
        mainView=view;
        this.ctx=ctx;
        adapter = new ArrayAdapter<TODOitem>(ctx, android.R.layout.simple_list_item_1, items);
        network=new Network(mainView, this);
    }

    @Override
    public void onAddBtnClicked() {
        String itemEntered = ((MainActivity)mainView).getItemET();
        ((MainActivity)mainView).setItemET("");
        network.getIDref().child(itemEntered).setValue("");

    }

    @Override
    public void onItemClicked(int position) {
        TODOitem removedItem=items.get(position); //get the position of the item the user wishes to remove
        network.getIDref().child(removedItem.title).removeValue(); //remove value from database
        items.remove(position);
        notifyAdapter();

    }

    @Override
    public void onLogoutBtnClicked() {
        FirebaseAuth.getInstance().signOut();
    }

    public ArrayList<TODOitem> getItems() {
        return items;
    }

    public void notifyAdapter(){
        adapter.notifyDataSetChanged();
    }

    public ArrayAdapter<TODOitem> getAdapter() {
        return adapter;
    }

    public void removeItem(TODOitem item){
        items.remove(item);
    }
}
