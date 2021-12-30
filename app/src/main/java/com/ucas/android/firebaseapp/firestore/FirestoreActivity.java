package com.ucas.android.firebaseapp.firestore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ucas.android.firebaseapp.R;
import com.ucas.android.firebaseapp.utils.Utils;
import com.ucas.android.firebaseapp.databinding.ActivityFirestoreBinding;
import com.ucas.android.firebaseapp.model.User;

import java.util.List;

public class FirestoreActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FirestoreActivity.class.getSimpleName();
    ActivityFirestoreBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirestoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        binding.saveSingleButton.setOnClickListener(this);
        binding.saveListButton.setOnClickListener(this);
        binding.readListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_single_button:
                saveSingleItem();
                break;
            case R.id.update_single_button:
                updateSingleItem();
                break;
            case R.id.save_list_button:
                saveList();
                break;
            case R.id.read_list_button:
                getList();
                break;
        }

    }

    private void saveSingleItem() {
        User user = new User("Abir", "abir@gmail.com", 989898, 28);
        db.collection("users").add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    String id = task.getResult().getId();
                    Log.d(TAG, "DocumentReference" + id);
                }

            }
        });
    }

    private void updateSingleItem() {
        User user = new User("Abir", "abir@gmail.com", 656565, 30);
        db.collection("users").document(user.getName()).set(user);
    }

    private void saveList() {
        for (User user : Utils.getUsers(this)) {
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentReference  " + documentReference.getId());

                        }
                    });
        }

    }

    private void getList(){
        db.collection("users")
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                   List<User> users= task.getResult().toObjects(User.class);
                    Log.d(TAG, "getList  " + users.toString());
                }

            }
        });

        db.collection("users")
                .whereGreaterThan("age",20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            List<User> users= task.getResult().toObjects(User.class);
                            Log.d(TAG, "getList  " + users.toString());
                        }

                    }
                });
    }
}