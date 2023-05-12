package com.davidvan.myprograms.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

   EditText edUsername, edEmail, edConfirmPassword, edPassword;
   Button btn;
   TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.username);
        edEmail = findViewById(R.id.email);
        edPassword = findViewById(R.id.password);
        edConfirmPassword = findViewById(R.id.confirmPassword);
        btn = findViewById(R.id.registerButton);
        tv = findViewById(R.id.existingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirmPassword.getText().toString();
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if(username.length()== 0 || password.length() == 0 || email.length() == 0 || confirm.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(password.compareTo(confirm) == 0){
                        if(isValidPassword(password)) {
                            db.register(username, email, password);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Password must contain at least 8 characters, having letter, digit and special symbol", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static boolean isValidPassword(String password) {
        boolean letter = false;
        boolean digit = false;
        boolean special = false;
        if(password.length() < 8 ) {
            return false;
        }else{
            for(int i = 0; i < password.length(); i++) {
                if(Character.isLetter(password.charAt(i))) {
                    letter = true;
                }
                if(Character.isDigit(password.charAt(i))){
                    digit = true;
                }
                char c = password.charAt(i);
                if(c>= 33 && c<=46 || c==64){
                    special = true;
                }
            }
        }

        if(letter && digit && special){
            return true;
        }else {
            return false;
        }
    }
}