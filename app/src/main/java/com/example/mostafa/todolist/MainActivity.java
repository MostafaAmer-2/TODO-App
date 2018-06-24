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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private Button add_btn;
    private Button logout_btn;
    private ListView itemsList;

    private ArrayList<String> items=new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private DatabaseReference dref= FirebaseDatabase.getInstance().getReference();;


    DatabaseReference usersRef = dref.child("users");
    DatabaseReference IDref = usersRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        add_btn = findViewById(R.id.add_btn);
        logout_btn=findViewById(R.id.logout_btn);
        itemsList= findViewById(R.id.items_list);

        //...here....//

        IDref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                items.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_btn:
                String itemEntered = itemET.getText().toString();
                itemET.setText("");
                IDref.child(itemEntered).setValue("");
                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout_btn:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String removedItem=items.get(position);
        adapter.notifyDataSetChanged();
        IDref.child(removedItem).removeValue();
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }
}


