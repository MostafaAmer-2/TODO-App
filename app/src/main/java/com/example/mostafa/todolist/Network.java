package com.example.mostafa.todolist;

import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Models.TODOitem;
import com.example.mostafa.todolist.Presenters.MainActivityPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Network {
    private MainPresenter mainPresenter;
    //Root node
    private DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    //Child from the root node: users
    private DatabaseReference usersRef = dref.child("users");
    //Child from the users node: depending on the id of the user
    private DatabaseReference IDref = usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    public Network(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        addChildEventListener();
    }

    /**
     * Method to add the child event listener to the reference
     */
    private void addChildEventListener() {
        IDref.addChildEventListener(new ChildEventListener() {
            /**
             * Add the new item present in the database to the ArrayList
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TODOitem itemToBeAdded = TODOitem.convertToItem(dataSnapshot);
                ((MainActivityPresenter) mainPresenter).addItem(itemToBeAdded);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            /**
             * Remove the item that was deleted from the database, from the ArrayList
             * @param dataSnapshot
             */
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ((MainActivityPresenter) mainPresenter).removeItem(TODOitem.convertToItem(dataSnapshot));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addItem(TODOitem newItem) {
        IDref.child(newItem.title).setValue("");
    }

    public void remove(TODOitem itemToBeRemoved) {
        IDref.child(itemToBeRemoved.title).removeValue();
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}
