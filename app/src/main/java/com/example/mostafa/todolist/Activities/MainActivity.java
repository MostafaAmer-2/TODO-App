package com.example.mostafa.todolist.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Interfaces.MainView;
import com.example.mostafa.todolist.Models.TODOitem;
import com.example.mostafa.todolist.Presenters.MainActivityPresenter;
import com.example.mostafa.todolist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * This class is responsible for showing and editing the TODO list
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MainView {

    private EditText itemET;
    private Button add_btn;
    private Button logout_btn;
    private ListView itemsList;



    private DatabaseReference dref= FirebaseDatabase.getInstance().getReference(); //root node
    //TODO: move to Interaction

    private MainPresenter mainPresenter;
  //  private M

    private DatabaseReference usersRef = dref.child("users"); //child from the root node: users
    private DatabaseReference IDref = usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //child from the users node: depending on the id of the user


    /**
     * The onCreate method instantiates all the instance variables with their corresponding values,
     * setting the action listeners for the buttons, and setting the child event listener to update the list
     * whenever an item is added or removed from the database upon creation of the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        add_btn = findViewById(R.id.add_btn);
        logout_btn=findViewById(R.id.logout_btn);
        itemsList= findViewById(R.id.items_list);

        mainPresenter=new MainActivityPresenter(this, getApplicationContext());

        IDref.addChildEventListener(new ChildEventListener() { //TODO: move to appropriate class
            /**
             * add the new item present in the database to the ArrayList
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ((MainActivityPresenter)mainPresenter).getItems().add(new TODOitem(dataSnapshot.getKey()));
                Log.i("hello", dataSnapshot.getKey());
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
                    if (item.title.equals((dataSnapshot.getKey())))
                        ((MainActivityPresenter)mainPresenter).getItems().remove(dataSnapshot.getKey());
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

        itemsList.setAdapter(((MainActivityPresenter)mainPresenter).getAdapter());
        getApplicationContext();
        add_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);

    }

    /**
     * specifying the action taken upon clicking different buttons. Switch statement to control the different behaviours
     * @param v The view which holds the buttons
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_btn: //adds the item to the database and empties the EditText field
                mainPresenter.onAddBtnClicked();
                displayItemAdded();
                break;

            case R.id.logout_btn: //logs out the user and finished the activity
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }

    public void displayItemAdded(){
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
    }
    public void displayItemDeleted(){
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }

    /**
     * the purpose of this method is to remove an item from the list and from the database upon clicking uon it.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainPresenter.onItemClicked(position);
        displayItemDeleted();
    }

    public String getItemET(){
        return itemET.getText().toString();
    }

    public void setItemET(String text){
        itemET.setText(text);
    }

    public DatabaseReference getIDref() {
        return IDref;
    }


}


