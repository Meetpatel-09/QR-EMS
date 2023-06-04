package com.example.myapplication.admin.event;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.myapplication.BuildConfig;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class GenerateQRCodeActivity extends AppCompatActivity {

    private DatePicker dpDate;
    private EditText etDate;
    private Button btn1, btn2;
    private ImageView imgQRCode;
    private Spinner eventSpinner;
    private String eventName, eventDate, qrUrl;

    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;

    String downloadUrl = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        dpDate = findViewById(R.id.dp_date);
        etDate = findViewById(R.id.et_date);
        imgQRCode = findViewById(R.id.qr_code_img);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        eventSpinner = findViewById(R.id.spinner_events);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventName = eventSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = dpDate.getDayOfMonth();
                int month = dpDate.getMonth() + 1;
                int year = dpDate.getYear();

                if (month < 10) {
                    if (day <10) {
                        eventDate = "0" + String.valueOf(day) + "-" + "0" + String.valueOf(month) + "-" + String.valueOf(year);
                    } else {
                        eventDate = String.valueOf(day) + "-" + "0" + String.valueOf(month) + "-" + String.valueOf(year);
                    }
                } else {
                    if (day <10) {
                        eventDate = "0" + String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
                    } else {
                        eventDate = String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year);
                    }
                }

                qrUrl = "https://www." + eventDate + eventName + ".com";

                MultiFormatWriter writer = new MultiFormatWriter();

                try {
                    BitMatrix matrix = writer.encode(qrUrl, BarcodeFormat.QR_CODE, 350, 350);

                    BarcodeEncoder encoder = new BarcodeEncoder();

                    Bitmap bitmap = encoder.createBitmap(matrix);

                    imgQRCode.setImageBitmap(bitmap);

                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    manager.hideSoftInputFromWindow(etDate.getApplicationWindowToken(), 0);

                    String qrcode = bitmap + ".jpg";

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] finalImage = byteArrayOutputStream.toByteArray();
                    final StorageReference filePath;
                    filePath = storageReference.child("Event").child(finalImage+"jpg");
                    final UploadTask uploadTask = filePath.putBytes(finalImage);
                    uploadTask.addOnCompleteListener(GenerateQRCodeActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                                uploadData();
                                            }
                                        });
                                    }
                                });
                            } else {
                                Toast.makeText(GenerateQRCodeActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(GenerateQRCodeActivity.this, AdminHomeActivity.class));
                            }
                        }
                    });

                    btn2.setVisibility(View.VISIBLE);

                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Drawable drawable = imgQRCode.getDrawable();
                            Bitmap bitmap1 = ((BitmapDrawable) drawable).getBitmap();

                            try {
                                File file = new File(getApplicationContext().getExternalCacheDir(), File.separator + qrcode);
                                FileOutputStream fOut = new FileOutputStream(file);
                                bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                                file.setReadable(true, false);
                                final Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);

                                intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.setType("image/jpg");

                                startActivity(Intent.createChooser(intent, "Share image via"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void uploadData() {
        dbRef = reference.child("Event");
        final String uniqueKey = dbRef.push().getKey();

        EventData eventData = new EventData(eventName, downloadUrl, eventDate, qrUrl,uniqueKey);
        dbRef.child(eventDate).child(uniqueKey).setValue(eventData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(GenerateQRCodeActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(GenerateQRCodeActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}