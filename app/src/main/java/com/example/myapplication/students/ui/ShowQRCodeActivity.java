package com.example.myapplication.students.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;

public class ShowQRCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qrcode);

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        String profileID = fUser.getUid();
        ImageView imgQRCode = findViewById(R.id.my_qr_code);
        Button btn2 = findViewById(R.id.btn_share);
        EditText etDate = findViewById(R.id.et_date);

        String qrUrl = "https://www." + profileID + ".com";

        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode(qrUrl, BarcodeFormat.QR_CODE, 550, 550);

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

            imgQRCode.setImageBitmap(bitmap);

            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            manager.hideSoftInputFromWindow(etDate.getApplicationWindowToken(), 0);

            String qrcode = bitmap + ".jpg";

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
                        final Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
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

//        FirebaseDatabase.getInstance().getReference().child("students").child(profileID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                StudentData data = snapshot.getValue(StudentData.class);
//
//                System.out.println("data.getUrl()");
//                System.out.println(data.getUrl());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}