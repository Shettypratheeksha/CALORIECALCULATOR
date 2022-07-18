package com.example.caloriecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, confirmpassword;
    Button register;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView btn = findViewById(R.id.alreadyHaveAccount);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        confirmpassword = findViewById(R.id.inputConfirmPassword);
        register = findViewById(R.id.btnRegister);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent(getApplicationContext(),RegisterActivity.class);
                // startActivity(i);
                startActivity(new Intent(RegisterActivity.this, Login.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }

            private void PerforAuth() {
                String username1 = username.getText().toString();
                String useremail = email.getText().toString();
                String password1 = password.getText().toString();
                String cpassword = confirmpassword.getText().toString();
                if (!useremail.matches(emailPattern)) {
                    email.setError("Enter correct Email");
                } else if (password1.isEmpty() || password1.length() < 6) {
                    password.setError("Enter proper password");
                } else if (!password1.equals(cpassword)) {
                    confirmpassword.setError("Password not match in both field");
                } else {
                    progressDialog.setMessage(" Please wait while Registration....");
                    progressDialog.setTitle(" Registration");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(useremail, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        private Object Intent;

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(RegisterActivity.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void sendUserToNextActivity() {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }
}