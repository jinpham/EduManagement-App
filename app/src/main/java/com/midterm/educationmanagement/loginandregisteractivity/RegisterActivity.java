package com.midterm.educationmanagement.loginandregisteractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.midterm.educationmanagement.ManagerActivity;
import com.midterm.educationmanagement.R;
import com.midterm.educationmanagement.dao.DaoTaiKhoan;
import com.midterm.educationmanagement.model.TaikhoanMatKhau;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private RelativeLayout rlayout;
    private Animation animation;
    EditText txtRegTk, txtRegMk, txtRegMkk;
    Button btDangKy, btNhapLai;
    ArrayList<TaikhoanMatKhau> listTk = new ArrayList<>();
    DaoTaiKhoan tkDao;

    RelativeLayout relativel_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        rlayout = findViewById(R.id.rlayout);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        rlayout.setAnimation(animation);

        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tkDao = new DaoTaiKhoan(RegisterActivity.this);

                String tk = txtRegTk.getText().toString();
                String mk = txtRegMk.getText().toString();
                String mkk = txtRegMkk.getText().toString();
                boolean xetTk = true, xetMk = false;
                TaikhoanMatKhau tkmk = new TaikhoanMatKhau(tk, mk);

                listTk = tkDao.getALl();

                if (mk.matches(mkk)) {
                    xetMk = true;
                } else {
                    xetMk = false;
                }

                for (int i = 0; i < listTk.size(); i++) {
                    TaikhoanMatKhau tkx = listTk.get(i);
                    if (tkx.getTenTaiKhoan().matches(tk)) {
                        xetTk = false;
                        break;
                    }
                }

                if (tk.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Tên tài khoản không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    if (mk.isEmpty() || mkk.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (xetTk == true) {
                            if (xetMk == true) {
                                tkDao.Them(tkmk);
                                Toast.makeText(RegisterActivity.this, "Thêm tài khoản thành công!", Toast.LENGTH_SHORT).show();

                                Intent i = new Intent();
                                i.putExtra("taikhoan",tk);
                                i.putExtra("matkhau",mk);
                                setResult(RESULT_OK,i);
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp nhau!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Tên tài khoản k được trùng!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    private void init() {
        relativel_layout=findViewById(R.id.relativel_layout);
       txtRegTk = findViewById(R.id.edtRegUser);
       txtRegMk = findViewById(R.id.edtRegPassword);
       txtRegMkk = findViewById(R.id.edtRePassword);
       btDangKy = findViewById(R.id.btnReg);
       btNhapLai = findViewById(R.id.btnRelay);
       if(ManagerActivity.isDark==true) {
           // dark theme is on
           relativel_layout.setBackgroundColor(getResources().getColor(R.color.black));
       }
       else
       {
           // light theme is on
           relativel_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.backdound_app));
       }
   }
}