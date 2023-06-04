package com.example.myapplication.admin.ebook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import com.example.myapplication.admin.AdminHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;

public class UploadPdf extends AppCompatActivity {

    private MaterialCardView addPdf;

    private final int REQ = 1;
    private Uri pdfData;

    private EditText pdfTitle;
    private Button uploadPdf;
    private TextView pdfTextview;

    private DatabaseReference reference;
    private StorageReference storageReference;

    private ProgressDialog pd;

    private String pdfName, title, department, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        pd = new ProgressDialog(this);

        addPdf = findViewById(R.id.add_pdf);
        pdfTitle = findViewById(R.id.pdf_title);
        uploadPdf = findViewById(R.id.upload_pdf_button);
        pdfTextview = findViewById(R.id.pdf_textview);

        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()) {
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                } else if(pdfData == null) {
                    Toast.makeText(UploadPdf.this, "Please select pdf", Toast.LENGTH_SHORT).show();
                } else {
                    Spinner sDepartment = findViewById(R.id.dept_s);
                    Spinner sSemester = findViewById(R.id.semester_s);

                    department = sDepartment.getSelectedItem().toString();
                    semester = sSemester.getSelectedItem().toString();

                    if (department.equals("Select Department")) {
                        Toast.makeText(UploadPdf.this, "Please select department.", Toast.LENGTH_SHORT).show();
                    } else if (department.equals("Select Semester")) {
                        Toast.makeText(UploadPdf.this, "Please select semester.", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                    }
                }
            }
        });
    }

    private void uploadFile() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading PDF");
        pd.show();
        StorageReference stora = storageReference.child("eBook/" + pdfName + "-" + System.currentTimeMillis() + ".pdf");
        stora.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();
                uploadData(String.valueOf(uri));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UploadPdf.this, AdminHomeActivity.class));
            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniqueKey = reference.child("pdf").push().getKey();

        HashMap hashMap = new HashMap();
        hashMap.put("pdfTitle", title);
        hashMap.put("pdfUrl", downloadUrl);
        hashMap.put("department", department);
        hashMap.put("semester", semester);

        reference.child("pdf").child(department).child(semester).child(uniqueKey).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
                startActivity(new Intent(UploadPdf.this, AdminHomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPdf.this, "Failed to upload pdf", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UploadPdf.this, AdminHomeActivity.class));
                finish();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file"), REQ);

    }

//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//                        if (pdfData.toString().startsWith("content://")) {
//                            Cursor cursor = null;
//                            try {
//                                cursor = UploadPdf.this.getContentResolver().query(pdfData, null, null, null, null);
//                                if (cursor != null && cursor.moveToFirst()) {
//                                    pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else if (pdfData.toString().startsWith("pdf://")) {
//                            pdfName = new File(pdfData.toString()).getName();
//                        }
//
//                        pdfTextview.setText(pdfName);
//
//                    }
//                }
//            });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK) {
            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = UploadPdf.this.getContentResolver().query(pdfData, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (pdfData.toString().startsWith("pdf://")) {
                pdfName = new File(pdfData.toString()).getName();
            }

            pdfTextview.setText(pdfName);
        }
    }
}