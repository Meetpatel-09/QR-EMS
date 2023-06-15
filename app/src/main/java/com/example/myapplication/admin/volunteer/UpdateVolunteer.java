package com.example.myapplication.admin.volunteer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateVolunteer extends AppCompatActivity {

    private ImageView updateTeacherImage;
    private EditText updateTeacherName, updateTeacherEmail, updateTeacherPost;
    private Button updateTeacherButton, deleteTeacherButton;

    private String name, email, post, image;
    private String uniqueKey, category;

    private String downloadUrl = "";
    private final int REQ = 1;
    private Bitmap bitmap = null;

    private DatabaseReference reference;
    private StorageReference storageReference;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_volunteer);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        post = getIntent().getStringExtra("post");
        image = getIntent().getStringExtra("image");

        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");

        pd = new ProgressDialog(this);

        updateTeacherImage = findViewById(R.id.update_teacher_image);
        updateTeacherName = findViewById(R.id.update_teacher_name);
        updateTeacherEmail = findViewById(R.id.update_teacher_email);
        updateTeacherPost = findViewById(R.id.update_teacher_post);
        updateTeacherButton = findViewById(R.id.update_teacher_button);
        deleteTeacherButton = findViewById(R.id.delete_teacher_button);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference();


        try {
            Picasso.get().load(image).into(updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTeacherName.setText(name);
        updateTeacherEmail.setText(email);
        updateTeacherPost.setText(post);

        updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updateTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        deleteTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void checkValidation() {
        name = updateTeacherName.getText().toString();
        email = updateTeacherEmail.getText().toString();
        post = updateTeacherPost.getText().toString();

        if (name.isEmpty()) {
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();
        } else if (email.isEmpty()) {
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();
        } else if (post.isEmpty()) {
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();
        } else if (bitmap == null) {
            pd.setMessage("Updating...");
            pd.show();
            updateData(image);
        } else {
            pd.setMessage("Updating...");
            pd.show();
            uploadImage();
        }
    }

    private void updateData(String s) {

        HashMap map = new HashMap();
        map.put("name", name);
        map.put("email", email);
        map.put("post", post);
        map.put("image", s);

        reference.child(category).child(uniqueKey).updateChildren(map).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                pd.dismiss();
                Toast.makeText(UpdateVolunteer.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateVolunteer.this, ViewVolunteer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateVolunteer.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] finalImage = byteArrayOutputStream.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Faculties").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(UpdateVolunteer.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                } else {
                    //   pd.dismiss();
                    Toast.makeText(UpdateVolunteer.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteData() {
        pd.setMessage("Deleting...");
        pd.show();
        reference.child(category).child(uniqueKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UpdateVolunteer.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateVolunteer.this, ViewVolunteer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateVolunteer.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateTeacherImage.setImageBitmap(bitmap);
        }
    }
}