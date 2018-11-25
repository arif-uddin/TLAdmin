package com.lazydevs.tinylensadmin.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.lazydevs.tinylensadmin.Helper.Helper;
import com.lazydevs.tinylensadmin.Model.ModelUser;
import com.lazydevs.tinylensadmin.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText FirstName, LastName, Email, PassWord;
    Button Register;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        helper = new Helper(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        FirstName = findViewById(R.id.et_first_name);
        LastName = findViewById(R.id.et_last_name);
        Email = findViewById(R.id.et_registration_email);
        PassWord = findViewById(R.id.et_registration_pass);
        Register = findViewById(R.id.bt_registration_register);
    }

    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void register(View view) {

        if(validate())
        {
            registerUser();
        }
    }

    boolean validate()
    {   if (!helper.isNameValid(FirstName.getText().toString()))// && helper.isPasswordValid(PassWord.getText().toString()))))
    {
        Toast.makeText(this, "First Name invalid", Toast.LENGTH_SHORT).show();
        return false;
    }else if(!helper.isNameValid(LastName.getText().toString()))
    {
        Toast.makeText(this, "Last Name invalid", Toast.LENGTH_SHORT).show();
        return false;
    }else if (!helper.isEmailValid(Email.getText().toString()))
    {
        Toast.makeText(this, "Email invalid", Toast.LENGTH_SHORT).show();
        return false;
    }else if (!helper.isPasswordValid(PassWord.getText().toString()))
    {
        Toast.makeText(this, "Password invalid", Toast.LENGTH_SHORT).show();
        return false;
    }else
    {
        return true;
    }
    }

    void registerUser()
    {
        firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString().trim(), PassWord.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            Log.d("WhatIstheTask", "" + task);
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            sendToDatabase(FirstName.getText().toString().trim(), LastName.getText().toString().trim(), Email.getText().toString().replaceAll(" ",""));

                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                        } else {
                            Log.d("WhatIstheTask", "" + task);
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
    }

    void sendToDatabase(String firstName, String lastName, String email) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
        ModelUser user = new ModelUser(firstName, lastName, email,null,firebaseAuth.getCurrentUser().getUid());
        databaseReference.child(getUID()).setValue(user);
    }

    private String getUID() {
        FirebaseUser currentFirebaseUser = firebaseAuth.getCurrentUser();
        return currentFirebaseUser.getUid();
    }

}
