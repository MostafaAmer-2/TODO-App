package com.example.mostafa.todolist;

import android.util.Log;

import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Interfaces.MainView;
import com.example.mostafa.todolist.Models.TODOitem;
import com.example.mostafa.todolist.Presenters.MainActivityPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Network {
    private MainView mainView;
    private MainPresenter mainPresenter;

    private DatabaseReference dref= FirebaseDatabase.getInstance().getReference(); //root node
    private DatabaseReference usersRef = dref.child("users"); //child from the root node: users
    private DatabaseReference IDref = usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //child from the users node: depending on the id of the user


    public Network(MainView mainView, final MainPresenter mainPresenter){
        this.mainView=mainView;
        this.mainPresenter=mainPresenter;

        IDref.addChildEventListener(new ChildEventListener() { //TODO: move to appropriate class
            /**
             * add the new item present in the database to the ArrayList
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TODOitem item=TODOitem.convertToItem(dataSnapshot);
                ((MainActivityPresenter)mainPresenter).getItems().add(item);
                ((MainActivityPresenter)mainPresenter).notifyAdapter();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            /**
             * remove the item that was deleted from the database, from the ArrayList
             * @param dataSnapshot
             */
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for(TODOitem item: ((MainActivityPresenter)mainPresenter).getItems()){
                    if (item.title.equals((dataSnapshot.getKey()))) {
                        ((MainActivityPresenter) mainPresenter).removeItem(item);
                    }
                }
                ((MainActivityPresenter)mainPresenter).notifyAdapter();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public DatabaseReference getIDref() {
        return IDref;
    }

}
