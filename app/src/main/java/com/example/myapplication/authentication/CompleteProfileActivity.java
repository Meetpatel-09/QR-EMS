package com.example.myapplication.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.admin.AdminHomeActivity;
import com.example.myapplication.admin.notice.FcmNotificationsSender;
import com.example.myapplication.admin.notice.NoticeData;
import com.example.myapplication.admin.notice.UploadNotice;
import com.example.myapplication.students.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CompleteProfileActivity extends AppCompatActivity {

    private ImageView iProfileImage, iIdCard;
    private TextView tProfileImage, tIdCard;
    private Button btnSubmit;

    private final int REQ = 1;
    private Bitmap bitmapProfile, bitmapIdCard;

    private DatabaseReference reference;
    private StorageReference storageReference;

    String downloadUrl1 = "", downloadUrl2 = "";
    private ProgressDialog pd;

    private FirebaseUser fUser;
    private String profileId;

    private boolean profile = false, idCard = false, isProfilePicked = false, isIdCardPicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        iProfileImage = findViewById(R.id.add_profile_image);
        iIdCard = findViewById(R.id.add_id_image);
        tProfileImage = findViewById(R.id.tv_add_profile_image);
        tIdCard = findViewById(R.id.tv_add_id_image);
        btnSubmit = findViewById(R.id.btn_submit);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profileId = fUser.getUid();

        pd = new ProgressDialog(this);

        btnSubmit.setOnClickListener(v -> {
            if (bitmapProfile == null) {
                Toast.makeText(this, "Please choose a profile picture", Toast.LENGTH_SHORT).show();
            } else if (bitmapIdCard == null) {
                Toast.makeText(this, "Please choose a id card", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        });

        iProfileImage.setOnClickListener(v -> {
            openGallery();
            profile = true;
            idCard = false;
        });


        tProfileImage.setOnClickListener(v -> {
            openGallery();
            profile = true;
            idCard = false;
        });


        iIdCard.setOnClickListener(v -> {
            openGallery();
            profile = false;
            idCard = true;
        });


        tIdCard.setOnClickListener(v -> {
            openGallery();
            profile = false;
            idCard = true;
        });
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        bitmapProfile.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream1);
        bitmapIdCard.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream2);
        byte[] finalImage1 = byteArrayOutputStream1.toByteArray();
        byte[] finalImage2 = byteArrayOutputStream2.toByteArray();
        final StorageReference filePath1;
        final StorageReference filePath2;
        filePath1 = storageReference.child("User").child(profileId).child(finalImage1.toString());
        filePath2 = storageReference.child("User").child(profileId).child(finalImage2.toString());

        final UploadTask uploadTask1 = filePath1.putBytes(finalImage1);
        final UploadTask uploadTask2 = filePath2.putBytes(finalImage2);
        uploadTask1.addOnCompleteListener(CompleteProfileActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl1 = String.valueOf(uri);
                                    uploadTask2.addOnCompleteListener(CompleteProfileActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                uploadTask2.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        filePath2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                            @Override
                                                            public void onSuccess(Uri uri) {
                                                                downloadUrl2 = String.valueOf(uri);
                                                                uploadData();
                                                            }
                                                        });
                                                    }
                                                });
                                            } else {
                                                pd.dismiss();
                                                Toast.makeText(CompleteProfileActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(CompleteProfileActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(CompleteProfileActivity.this, AdminHomeActivity.class));
                }
            }
        });
    }

    private void uploadData() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("profilePhoto", downloadUrl1);
        map.put("idPhoto", downloadUrl2);
        map.put("isProfileComplete", "Yes");

        reference.child("students").child(profileId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
//                Toast.makeText(CompleteProfileActivity.this, "Notice Uploaded", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(CompleteProfileActivity.this, MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(CompleteProfileActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(CompleteProfileActivity.this, AdminHomeActivity.class));
                finish();
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                if (profile) {
                    bitmapProfile = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } else {
                    bitmapIdCard = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (profile) {
                iProfileImage.setImageBitmap(bitmapProfile);
            } else {
                iIdCard.setImageBitmap(bitmapIdCard);
            }
        }
    }
}