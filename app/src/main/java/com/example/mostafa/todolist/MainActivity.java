package com.example.mostafa.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private Button add_btn;
    private Button logout_btn;
    private ListView itemsList;

    private ArrayList<String> items=new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private DatabaseReference dref= FirebaseDatabase.getInstance().getReference(); //root node


    DatabaseReference usersRef = dref.child("users"); //child from the root node: users
    DatabaseReference IDref = usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()); //child from the users node: depending on the id of the user


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

        IDref.addChildEventListener(new ChildEventListener() {
            /**
             * add the new item present in the database to the ArrayList
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                items.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
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
                items.remove(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        itemsList.setAdapter(adapter);

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
                String itemEntered = itemET.getText().toString();
                itemET.setText("");
                IDref.child(itemEntered).setValue("");
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_btn: //logs out the user and finished the activity
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
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
        String removedItem=items.get(position); //get the position of the item the user wishes to remove
        adapter.notifyDataSetChanged();
        IDref.child(removedItem).removeValue(); //remove value from database
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }
}


