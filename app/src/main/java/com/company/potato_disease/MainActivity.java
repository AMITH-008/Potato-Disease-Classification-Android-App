package com.company.potato_disease;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button videoCapture, imageCapture, imageSelect, imageUpload;

    private  static final int CAMERA_REQUEST_CODE = 2;

    private  static final int VIDEO_CAPTURE_CODE = 5;

    private static final String TAG = "AKN";

    private static final int IMAGE_CAPTURE_CODE = 200;

    private Uri videoPath;
    private File imageFile;
    private String imagePath;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoCapture = findViewById(R.id.videoCapture);
        imageCapture = findViewById(R.id.imageCapture);
        imageSelect = findViewById(R.id.imageSelect);
        imageUpload = findViewById(R.id.imageUpload);
        imageView = findViewById(R.id.image);

        if(isCameraPresentInPhone()) {
            Log.i(TAG, "Camera Detected");
            getCameraPermission();
        }else{
            Toast.makeText(getApplicationContext(), R.string.camera_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        imageCapture.setOnClickListener(cameraCaptureClicked);
        videoCapture.setOnClickListener(videoCaptureClicked);


    }
    public View.OnClickListener cameraCaptureClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            if(ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
//                Toast.makeText(view.getContext(), "Requires Camera Permission", Toast.LENGTH_SHORT).show();
//                return;
//            }
            Toast.makeText(view.getContext(), "Camera Capturing Started", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Camera Capture Started");
            captureImage();
        }
    };
    public View.OnClickListener videoCaptureClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                Toast.makeText(view.getContext(), "Requires Camera Permission", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(view.getContext(), "Video Capturing Started", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Video Capture Started");
            recordVideo();
        }
    };

    public View.OnClickListener selectImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectImage();
        }
    };

    public View.OnClickListener uploadImageClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            uploadImage();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VIDEO_CAPTURE_CODE) {
            if(resultCode == RESULT_OK ) {

                assert data != null;
                videoPath = data.getData();

                Log.i(TAG, "Video Is Recorded and available at path: "+ videoPath);
            }else if(resultCode == RESULT_CANCELED) {
                Log.i(TAG, "Video Recording Cancelled");
            }else{
                Log.i(TAG, "Error Occurred");
            }
        }

        if(requestCode == IMAGE_CAPTURE_CODE) {
            if(resultCode == RESULT_OK) {
                try{

                    Log.i(TAG, "Image is captured and available at path: "+ imagePath);

                    Bitmap b = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(b);

                }catch (Exception e){
                    Log.i(TAG, e.getMessage());
                    return;
                }

            }else if(resultCode == RESULT_CANCELED) {
                Log.i(TAG, "Video Recording Cancelled");
            }else{
                Log.i(TAG, "Error Occurred");
            }
        }
    }

    private File createFile() throws IOException {
        String time  = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String filename = "image_"+time+"_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(filename, ".jpg", storageDirectory);
        Log.i(TAG, "createFile: "+ image.toString());
        return image;
    }

    private boolean isCameraPresentInPhone() {
        return getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE_CODE);
    }

    private void captureImage() {
        try{
            imageFile = createFile();
        }catch (IOException e){
            Log.i(TAG, "CaptureImage "+e.getMessage());
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(imageFile != null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            imagePath = "file:"+imageFile.getAbsolutePath();
        }
        startActivityForResult(intent, IMAGE_CAPTURE_CODE);
    }

    private void selectImage() {

    }

    private void uploadImage() {

    }

}