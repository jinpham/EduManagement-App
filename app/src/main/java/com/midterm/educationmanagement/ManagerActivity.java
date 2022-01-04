package com.midterm.educationmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.midterm.educationmanagement.loginandregisteractivity.ChangePasswordActivity;
import com.midterm.educationmanagement.loginandregisteractivity.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class ManagerActivity extends AppCompatActivity  {
    ImageView btnLop, btnSinhVien, btnInFo, btnDangXuat;
    Animation animation;
    GridLayout gridLayout;
    TextView tv1, tv2, tv3, tvInFo;
    ImageView imageViewMenu;
    Intent intent;
    public static boolean isDark = false;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        linearLayout=findViewById(R.id.linearLayout);
        isDark = getThemeStatePref();
        if(isDark) {
            // dark theme is on
            linearLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else
        {
            // light theme is on
            linearLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }
        btnLop = findViewById(R.id.btnLop);
        gridLayout = findViewById(R.id.girdviewManager);
        btnSinhVien = findViewById(R.id.btnsinhvien);
        tv1 = findViewById(R.id.tvmana1);
        tv2 = findViewById(R.id.tvmana2);
        tv3 = findViewById(R.id.tvdangxuat);
        btnInFo = findViewById(R.id.btninfo);
        tvInFo = findViewById(R.id.tvinfo);
        btnDangXuat = findViewById(R.id.imageViewdangxuat);
        imageViewMenu=findViewById(R.id.imageViewMenu);
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view=(ImageView)v;
                final PopupMenu popupMenu=new PopupMenu(ManagerActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.drawer_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){
                            case R.id.menuDoimatkhau:
                                intent=new Intent(ManagerActivity.this, ChangePasswordActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
                                break;
                            case R.id.menuDoiTheme:
                                isDark = !isDark ;
                                if (isDark) {
                                    linearLayout.setBackgroundColor(getResources().getColor(R.color.black));
                                }
                                else {
                                    linearLayout.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                                saveThemeStatePref(isDark);
                                break;
                            case R.id.menuDangXuat:
                                intent=new Intent(ManagerActivity.this,LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
                                break;
                        }
                        return true;
                    }
                });

            }

        });


        animation = AnimationUtils.loadAnimation(ManagerActivity.this, R.anim.bottom_top);
        gridLayout.setAnimation(animation);
        btnInFo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, InFoAppActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tvInFo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, InFoAppActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, DanhSachLopActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        btnLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, DanhSachLopActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        btnSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachLopActivity.xetList = false;
                startActivity(new Intent(ManagerActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, DanhSachLopActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
    }


    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref2",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private boolean getThemeStatePref () {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref2",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }


}
