package com.hellomazid.interviewtest;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screen4Activity extends AppCompatActivity {

    private DrawingView drawingView;
    private static final String TAG = "SCREEN_4_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Screen 4");
        setContentView(R.layout.activity_screen4);
        drawingView = (DrawingView) findViewById(R.id.drawing_view);
    }

    public void submit(View view) {
        Bitmap bitmap = drawingView.getBitmap();
        storeImage(bitmap);
        Toast.makeText(this, "Signature is saved in SD card", Toast.LENGTH_SHORT).show();
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputFile();
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (Exception e) {
            Toast.makeText(this, "Sorry something goes wrong", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputFile(){
        File outputDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files");

        if(!outputDir.exists()){
            outputDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IT_"+ timeStamp +".png";
        File outputFile = new File(outputDir.getPath() + File.separator + fileName);
        return outputFile;
    }
}
