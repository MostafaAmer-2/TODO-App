package com.example.mostafa.todolist.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mostafa.todolist.Activities.Frags;
import com.example.mostafa.todolist.Activities.login_page;
import com.example.mostafa.todolist.Activities.register_page;
import com.example.mostafa.todolist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEmailField= (EditText) view.findViewById(R.id.emailField);
        mPasswordField= (EditText) view.findViewById(R.id.passwordField);
        mRegisterBtn= (Button) view.findViewById(R.id.registerBtn);

        //TODO: Unable to add the button action listener until now. The app crashes when I add it
//        mRegisterBtn.setOnClickListener(new View.OnClickListener(){
//
//            /**
//             *starting the registration process once the "register" button gets clicked
//             * @param view
//             */
//            @Override
//            public void onClick(View view) {
//                startRegistration();
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void startRegistration(){
        final String email= mEmailField.getText().toString();
        final String password=mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) { //checking that the fields are not left empty
            Toast.makeText(getActivity(), "Empty Fields", Toast.LENGTH_LONG).show();
        }
        else if (!email.contains("@")){ //checking that the email is inserted in correct format
            Toast.makeText(getActivity(), "Incorrect e-mail format", Toast.LENGTH_LONG).show();
        }
        else if (password.length()<6) { //checking that password meets the required length
            Toast.makeText(getActivity(), "Password must contain 6 characters or more", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getActivity(), "Registration Problem", Toast.LENGTH_LONG).show();
                    }
                    else {
                        FirebaseAuth.getInstance().signOut();
                        ((Frags)getActivity()).getViewPager().setCurrentItem(0); //to return back to the login tab
                        //Intent goToLogin = new Intent(getActivity(), login_page.class);
                        //goToLogin.putExtra("email", email);
                        //goToLogin.putExtra("password", password);
                        //startActivity(goToLogin);

                    }
                }
            });
        }
    }
}
