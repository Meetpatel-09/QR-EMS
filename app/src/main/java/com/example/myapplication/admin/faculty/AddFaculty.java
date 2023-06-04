package com.example.myapplication.admin.faculty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import com.example.myapplication.admin.AdminHomeActivity;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddFaculty extends AppCompatActivity {

    private ImageView addTeacherImage;
    private EditText addTeacherName, addTeacherEmail, addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeacherButton;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String category;
    private String name, email, post;
    private String downloadUrl = "";
    private ProgressDialog pd;
    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        addTeacherImage = findViewById(R.id.add_teacher_image);
        addTeacherName = findViewById(R.id.add_teacher_name);
        addTeacherEmail = findViewById(R.id.add_teacher_email);
        addTeacherPost = findViewById(R.id.add_teacher_post);
        addTeacherCategory = findViewById(R.id.add_teacher_category);
        addTeacherButton = findViewById(R.id.add_teacher_button);
        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        storageReference = FirebaseStorage.getInstance().getReference();

        String [] items = new String[]{"Select Department", "Civil", "Computer", "Mechanical"};
        addTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items));
        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = addTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        name = addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post = addTeacherPost.getText().toString();

        if (name.isEmpty()) {
            addTeacherName.setError("Empty");
            addTeacherName.requestFocus();
        } else if (email.isEmpty()) {
            addTeacherEmail.setError("Empty");
            addTeacherEmail.requestFocus();
        } else if (post.isEmpty()) {
            addTeacherPost.setError("Empty");
            addTeacherPost.requestFocus();
        } else if (category.equals("Select Department")) {
            Toast.makeText(this, "Please select department", Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            pd.setMessage("Uploading...");
            pd.show();
            insertData();
        } else {
            pd.setMessage("Uploading...");
            pd.show();
            insertImage();
        }
    }

    private void insertImage() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] finalImage = byteArrayOutputStream.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Faculties").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);
        uploadTask.addOnCompleteListener(AddFaculty.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    insertData();
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(AddFaculty.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddFaculty.this, AdminHomeActivity.class));
                }
            }
        });
    }

    private void insertData() {
        dbRef = reference.child(category);
        final String uniqueKey = dbRef.push().getKey();

        FacultyData facultyData = new FacultyData(name, email, post, downloadUrl, uniqueKey);
        dbRef.child(uniqueKey).setValue(facultyData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Faculty Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddFaculty.this, AdminHomeActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddFaculty.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddFaculty.this, AdminHomeActivity.class));
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
            addTeacherImage.setImageBitmap(bitmap);
        }
    }
}