package com.ucas.android.firebaseapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.ucas.android.firebaseapp.databinding.ActivityLoginBinding;
import com.ucas.android.firebaseapp.storage.StorageActivity;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            goToNext();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.register.setEnabled(true);
        binding.login.setEnabled(true);
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loading.setVisibility(View.VISIBLE);
                String email = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
                    register(email, password);
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loading.setVisibility(View.VISIBLE);
                String email = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
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
                    goToNext();
                } else {
                    Toast.makeText(getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("error", task.getException().toString());
                }

            }
        })
        ;
    }

    private void goToNext() {
        startActivity(new Intent(getApplicationContext(), StorageActivity.class));
    }
}