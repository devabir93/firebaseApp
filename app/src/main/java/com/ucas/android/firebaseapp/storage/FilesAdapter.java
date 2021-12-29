package com.ucas.android.firebaseapp.storage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.ucas.android.firebaseapp.R;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MyViewHolder> {

    List<StorageReference> references;
    Context context;

    public FilesAdapter(Activity activity) {
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        StorageReference reference = references.get(position);

        holder.name.setText(reference.getName());
        // implement setOnClickListener event on item view.
        if(position==0) {
            try {
                File localFile = File.createTempFile("abir", "jpg");
                reference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                holder.iv.setImageBitmap(myBitmap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                e.printStackTrace();
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Download directly from StorageReference using Glide
                Glide.with(context)
                        .load(reference)
                        .into(holder.iv);
            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the file
                reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                        Toast.makeText(context, "File deleted successfully", Toast.LENGTH_SHORT).show();
                        references.remove(position);
                        notifyItemRemoved(position);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        exception.printStackTrace();
                        Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public int getItemCount() {
        return references != null ? references.size() : 0;
    }

    public void setData(List<StorageReference> references) {
        this.references = references;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, delete,download;// init the item view's
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.title);
            delete = (TextView) itemView.findViewById(R.id.delete);
            download = (TextView) itemView.findViewById(R.id.download);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
