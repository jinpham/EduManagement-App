package com.midterm.educationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

public class InFoAppActivity extends AppCompatActivity {
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_app);
        relativeLayout=findViewById(R.id.relativel_layout);
        if(ManagerActivity.isDark==true) {
            // dark theme is on
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else
        {
            // light theme is on
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
}
