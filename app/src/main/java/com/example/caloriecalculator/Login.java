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

public class Login extends AppCompatActivity {

    EditText email,password;
    TextView btn;
    Button login;
    ProgressDialog progressDialog;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= findViewById(R.id.btnLogin);
        email=findViewById(R.id.inputEmail);
        password=findViewById(R.id.inputPassword);
        login=findViewById(R.id.btnLogin);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i=new Intent(getApplicationContext(),RegisterActivity.class);
                // startActivity(i);
                startActivity(new Intent(Login.this,RegisterActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }

            private void performLogin() {
                String useremail = email.getText().toString();
                String password1 = password.getText().toString();

                if (!useremail.matches(emailPattern)) {
                    email.setError("Enter correct Email");
                } else if (password1.isEmpty() || password1.length() < 6) {
                    password.setError("Enter proper password");
                } else {
                    progressDialog.setMessage(" Please wait while Login....");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(useremail, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(Login.this, "Login successfull", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void sendUserToNextActivity() {
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }


                    });

                }
            }
        });
    }
}