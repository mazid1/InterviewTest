package com.hellomazid.interviewtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void parseJSON(View view) {
        Intent intent = new Intent(this, ParseJsonActivity.class);
        startActivity(intent);
    }

    public void parseXML(View view) {
        Intent intent = new Intent(this, ParseXmlActivity.class);
        startActivity(intent);
    }

    public void parseHTML(View view) {
        Intent intent = new Intent(this, ParseHtmlActivity.class);
        startActivity(intent);
    }

    public void goToScreen4(View view) {
        Intent intent = new Intent(this, Screen4Activity.class);
        startActivity(intent);
    }
}
