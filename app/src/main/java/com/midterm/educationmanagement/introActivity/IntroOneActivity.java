package com.midterm.educationmanagement.introActivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.midterm.educationmanagement.loginandregisteractivity.LoginActivity;
import com.midterm.educationmanagement.R;

public class IntroOneActivity extends AppCompatActivity {
    ImageView logo, imageViewOngVang,bacgroundApp;
    TextView tvAppName;
    Animation leftoright, righttoleft, topBottom,aniOngVang,anibg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_one);


        logo = findViewById(R.id.logo);
        tvAppName = findViewById(R.id.tvAppName);
        bacgroundApp=findViewById(R.id.bgapp);

        aniOngVang = AnimationUtils.loadAnimation(IntroOneActivity.this, R.anim.scale);
        anibg = AnimationUtils.loadAnimation(IntroOneActivity.this, R.anim.background_intro_animation);


        leftoright = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        righttoleft = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        topBottom = AnimationUtils.loadAnimation(this, R.anim.bottomtop);

        logo.setAnimation(righttoleft);
        tvAppName.setAnimation(leftoright);
        bacgroundApp.setAnimation(anibg);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroOneActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
