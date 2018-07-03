package com.example.mostafa.todolist.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mostafa.todolist.Interfaces.MainPresenter;
import com.example.mostafa.todolist.Interfaces.MainView;
import com.example.mostafa.todolist.Presenters.MainActivityPresenter;
import com.example.mostafa.todolist.R;

import butterknife.BindView;

/**
 * This class is responsible for showing and editing the todoList
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, MainView {

    @BindView(R.id.item_edit_text)
    EditText itemET;
    @BindView(R.id.add_btn)
    Button add_btn;
    @BindView(R.id.logout_btn)
    Button logout_btn;
    @BindView(R.id.items_list)
    ListView itemsList;
    private MainPresenter mainPresenter;


    /**
     * The onCreate method instantiates all the instance variables with their corresponding values,
     * setting the action listeners for the buttons, and setting the child event listener to update the list
     * whenever an item is added or removed from the database upon creation of the activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemET = findViewById(R.id.item_edit_text);
        add_btn = findViewById(R.id.add_btn);
        logout_btn = findViewById(R.id.logout_btn);
        itemsList = findViewById(R.id.items_list);

        mainPresenter = new MainActivityPresenter(this, getApplicationContext());


        itemsList.setAdapter(((MainActivityPresenter) mainPresenter).getAdapter());
        add_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);
        itemsList.setOnItemClickListener(this);

    }

    /**
     * specifying the action taken upon clicking different buttons. Switch statement to control the different behaviours
     *
     * @param v The view which holds the buttons
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn: //adds the item to the database and empties the EditText field
                mainPresenter.onAddBtnClicked();
                displayItemAdded();
                break;

            case R.id.logout_btn: //logs out the user and finished the activity
                mainPresenter.onLogoutBtnClicked();
                finish();
                break;
        }
    }

    public void displayItemAdded() {
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
    }

    public void displayItemDeleted() {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }

    /**
     * the purpose of this method is to remove an item from the list and from the database upon clicking uon it.
     *
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

    public String getItemET() {
        return itemET.getText().toString();
    }

    public void setItemET(String text) {
        itemET.setText(text);
    }

}


