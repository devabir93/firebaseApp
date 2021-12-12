package com.ucas.android.firebaseapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ucas.android.firebaseapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        binding.register.setEnabled(true);
        binding.login.setEnabled(true);
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loading.setVisibility(View.VISIBLE);
                String email = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                register(email, password);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loading.setVisibility(View.VISIBLE);
                String email = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                    login(email, password);
            }
        });
    }

    private void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                binding.loading.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Welcome" + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("error", task.getException().toString());
                }

            }
        })
        ;
    }
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                binding.loading.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), "Welcome" + firebaseUser.getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("error", task.getException().toString());
                }

            }
        })
        ;
    }
}