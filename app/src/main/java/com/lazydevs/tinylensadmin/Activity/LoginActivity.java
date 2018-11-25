package com.lazydevs.tinylensadmin.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lazydevs.tinylensadmin.Helper.Helper;
import com.lazydevs.tinylensadmin.R;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText pass;
    TextView Register;
    CheckBox checkBoxShowPassword;
    Button login;
    FirebaseAuth firebaseAuth;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.et_email);
        pass= findViewById(R.id.et_pass);
        login = (Button) findViewById(R.id.bt_login);

        Intent intent;
        if (user!=null){
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper=new Helper();
                String Email = email.getText().toString().replaceAll(" ","");
                String Pass = pass.getText().toString();
                if (Email.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();

                }
                else {

                    boolean isEmailValid=helper.isEmailValid(Email);

                    if (isEmailValid)
                    {
                        if (Pass.isEmpty())
                        {
                            Toast.makeText(LoginActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();

                        }

                        else {

                            boolean isPasswordValid= helper.isPasswordValid(Pass);

                            if(isPasswordValid)
                            {
                                loginUser(Email,Pass);
                            }

                            else
                            {
                                Toast.makeText(LoginActivity.this, "Password must be at least 6 character", Toast.LENGTH_SHORT).show();

                            }
                        }

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Email is invalid!", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

    public void tv_register(View view) {
        Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);

    }

    void loginUser(String Email,String Pass)
    {
        firebaseAuth.signInWithEmailAndPassword(Email, Pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(user!=null)
                            {
                                Toast.makeText(LoginActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            //updateUI(user);
                        } else {

                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed!",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    protected void onStop() {
        super.onStop();
        finish();
    }

}
