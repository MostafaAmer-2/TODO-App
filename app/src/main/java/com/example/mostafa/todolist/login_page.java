package com.example.mostafa.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class is responsible for collecting credentials from the user and starting the sign in process.
 */
public class login_page extends AppCompatActivity {


    private EditText emailField;
    private EditText passwordField;
    private Button loginBtn;
    private Button registerBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    /**
     * The onCreate method instantiates all the instance variables with their corresponding values,
     * setting the action listeners for the buttons and setting the Authentication Listener.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth=FirebaseAuth.getInstance();

        emailField= (EditText) findViewById(R.id.emailField);
        passwordField= (EditText) findViewById(R.id.passwordField);
        loginBtn= (Button) findViewById(R.id.loginBtn);
        registerBtn= (Button) findViewById(R.id.registerBtn);

        mAuthListener=new FirebaseAuth.AuthStateListener() {
            /**
             * the purpose of this method is to detect the change in state of the authentication, whether the user
             * gets signed in, signed out, ..etc
             * @param firebaseAuth Firebase Authentication Instance
             */
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){ //if there exists a user who is signed in right now
                    startActivity(new Intent(login_page.this, MainActivity.class));
                }
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener(){

            /**
             * Calling the startSignIn method once the login button gets clicked
             * @param view the biew containing the button
             */
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener(){

            /**
             * Calling the goToRegistration method once the register button gets clicked
             * @param view the view containing the button
             */
            @Override
            public void onClick(View view) {
                goToRegistrationPage();
            }
        });

    }

    /**
     * set the EditText fields of email and password to empty as the activity starts, and set the Auth State Listener
     */
    protected void onStart(){
        super.onStart();

        emailField.setText("");
        passwordField.setText("");

        mAuth.addAuthStateListener(mAuthListener);
    }

    /**
     * Make sure the user gets signed out, as the activity stops
     */
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            FirebaseAuth.getInstance().signOut();
        }
    }

    /**
     * startSignIn method collects the email and password of the user and tries to sign them in
     * after checking that they meet the preliminary checks. In case of any error, a toast is shown to the user.
     */
    private void startSignIn(){
        String email= emailField.getText().toString();
        String password=passwordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){ //conndition to ensure the fields are not left empty
            Toast.makeText(login_page.this, "Empty Fields", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                /**
                 * the onComplete method is used to show the status of the task it's invoked on,
                 * whether it's successful or not.
                 * @param task the sign in task
                 */
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){ //sign in failed
                        Toast.makeText(login_page.this, "Sign In Problem", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /**
     *This method starts a new activity responsible for the registration
     */
    private void goToRegistrationPage(){
        startActivity(new Intent(login_page.this, register_page.class));
    }

}

