package com.company.potato_disease;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    Button videoCapture;

    private  static final int CAMERA_REQUEST_CODE = 2;

    private  static final int VIDEO_CAPTURE_CODE = 5;

    private static final String TAG = "AKN";

    private Uri videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoCapture = findViewById(R.id.videoCapture);

        if(isCameraPresentInPhone()) {
            Log.i(TAG, "Camera Detected");
            getCameraPermission();
        }else{
            Toast.makeText(getApplicationContext(), R.string.camera_not_found, Toast.LENGTH_SHORT).show();
            return;
        }

        videoCapture.setOnClickListener(videoCaptureClicked);

    }

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
    }

    private boolean isCameraPresentInPhone() {
        return getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    public void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_CAPTURE_CODE);
    }
}