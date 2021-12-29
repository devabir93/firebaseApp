package com.ucas.android.firebaseapp.storage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.ucas.android.firebaseapp.auth.BaseActivity;
import com.ucas.android.firebaseapp.databinding.ActivityAllUserFilesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllUserFilesActivity extends BaseActivity {

    ActivityAllUserFilesBinding binding;
    private FilesAdapter adapter;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUserFilesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storageReference = firebaseStorage.getReference().child("images/"+currentUser.getUid());
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        adapter = new FilesAdapter(AllUserFilesActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.rv.setLayoutManager(linearLayoutManager);
        binding.rv.setAdapter(adapter); // set the Adapter to RecyclerView
        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        adapter.setData(listResult.getItems());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

/*
        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {
                            // All the prefixes under listRef.
                            // You may call listAll() recursively on them.
                            Log.d("AllUserFilesActivity", "prefix " + prefix.getName() + prefix.getDownloadUrl());
                        }

                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            Log.d("AllUserFilesActivity", "item " + item.getName() + item.getDownloadUrl());
                            references.add(item);
                        }

                        adapter.setData(references);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });*/
    }
}