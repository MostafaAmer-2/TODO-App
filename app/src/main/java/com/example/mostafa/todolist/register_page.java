package com.example.mostafa.todolist;

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

public class register_page extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        mEmailField= (EditText) findViewById(R.id.emailField);
        mPasswordField= (EditText) findViewById(R.id.passwordField);
        mRegisterBtn= (Button) findViewById(R.id.registerBtn);

        mAuth=FirebaseAuth.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startRegistration();
            }
        });

    }

    protected void onStart(){
        super.onStart();

        mEmailField.setText("");
        mPasswordField.setText("");
    }

    private void startRegistration(){
        String email= mEmailField.getText().toString();
        String password=mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(register_page.this, "Empty Fields", Toast.LENGTH_LONG).show();
        }
        else if (!email.contains("@")){
            Toast.makeText(register_page.this, "Password must contain 6 characters or more", Toast.LENGTH_LONG).show();
        }
        else if (password.length()<6) {
                Toast.makeText(register_page.this, "Incorrect e-mail format", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
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
