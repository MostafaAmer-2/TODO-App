package com.example.mostafa.todolist.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mostafa.todolist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class is responsible for collecting credentials from the user required for sign up
 */
public class register_page extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;

    /**
     *The onCreate method instantiates all the instance variables with their corresponding values, and
     * setting the action listeners for the buttons.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mEmailField= (EditText) findViewById(R.id.emailField);
        mPasswordField= (EditText) findViewById(R.id.passwordField);
        mRegisterBtn= (Button) findViewById(R.id.registerBtn);

        mAuth=FirebaseAuth.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener(){

            /**
             *starting the registration process once the "register" button gets clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
                startRegistration();
            }
        });

    }

    /**
     * set the EditText fields of email and password to empty as the activity starts
     */
    protected void onStart(){
        super.onStart();

        mEmailField.setText("");
        mPasswordField.setText("");
    }

    /**
     * startRegistration method collects the email and password of the user and tries to register them
     * after checking that they meet the preliminary checks. In case of any error, a toast is shown to the user.
     */
    private void startRegistration(){
        String email= mEmailField.getText().toString();
        String password=mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) { //checking that the fields are not left empty
            Toast.makeText(register_page.this, "Empty Fields", Toast.LENGTH_LONG).show();
        }
        else if (!email.contains("@")){ //checking that the email is inserted in correct format
            Toast.makeText(register_page.this, "Incorrect e-mail format", Toast.LENGTH_LONG).show();
        }
        else if (password.length()<6) { //checking that password meets the required length
                Toast.makeText(register_page.this, "Password must contain 6 characters or more", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                /**
                 * the onComplete method is used to show the status of the task it's invoked on,
                 * whether it's successful or not.
                 * @param task the registration task
                 */
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){ //registration failed
                        Toast.makeText(register_page.this, "Registration Problem", Toast.LENGTH_LONG).show();
                    }
                    else {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                }
            });
        }
    }
}
