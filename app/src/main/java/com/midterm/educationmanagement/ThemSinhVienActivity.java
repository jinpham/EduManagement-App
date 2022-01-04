package com.midterm.educationmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.midterm.educationmanagement.dao.LopDao;
import com.midterm.educationmanagement.dao.SinhVienDao;
import com.midterm.educationmanagement.model.Lop;
import com.midterm.educationmanagement.model.SinhVien;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemSinhVienActivity extends AppCompatActivity {
    EditText edtTensv, edtMasv, edtemail, edtHinh;
    Spinner spMaLop;
    Button btnThem,  btnDanhSach, btnReview;

    SinhVienDao daoSach;
    LopDao lsDao;

    LinearLayout linearLayout;
    CircleImageView imgAvata;
    ArrayList<Lop> lopList = new ArrayList<>();
    Animation animation;

    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sinh_vien);
        lsDao = new LopDao(ThemSinhVienActivity.this);

        linearLayout = findViewById(R.id.linearLayout);
        edtMasv = findViewById(R.id.txtMaSV);
        edtTensv = findViewById(R.id.txtTenSV);
        edtHinh = findViewById(R.id.txtHinh);
        edtemail = findViewById(R.id.txtemail);
        spMaLop = findViewById(R.id.txtMalop);
        btnThem = findViewById(R.id.btntThem);

        btnDanhSach = findViewById(R.id.btnDanhSach);
        btnReview = findViewById(R.id.btnReviewThem);
        imgAvata = findViewById(R.id.imgAvata);

        daoSach = new SinhVienDao(ThemSinhVienActivity.this);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        linearLayout.setAnimation(animation);

        relativeLayout=findViewById(R.id.relativel_layout);
        if(ManagerActivity.isDark==true) {
            // dark theme is on
            relativeLayout.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else
        {
            // light theme is on
            relativeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.backdound_app));
        }

        btnDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemSinhVienActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        lopList = lsDao.getAll();
        ArrayAdapter adapter = new ArrayAdapter(ThemSinhVienActivity.this, android.R.layout.simple_spinner_item, lopList);
        spMaLop.setAdapter(adapter);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtHinh.getText().toString().equalsIgnoreCase("")){
                    imgAvata.setImageResource(R.drawable.avatamacdinh);
                }else if(edtHinh.getText().toString()!=""){
                    int id_hinh = ((Activity)ThemSinhVienActivity.this).getResources().getIdentifier(edtHinh.getText().toString(), "drawable", ((Activity) ThemSinhVienActivity.this).getPackageName());
                    imgAvata.setImageResource(id_hinh);
                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    String ma = edtMasv.getText().toString();
                    String ten = edtTensv.getText().toString();
                    String email = edtemail.getText().toString();
                    String hinh = edtHinh.getText().toString();
                    Lop ls = (Lop) spMaLop.getSelectedItem();
                    String maLop = ls.getMaLop();
                    if (ma.equals("")) {
                        Toast.makeText(ThemSinhVienActivity.this, "Mã sinh viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (ten.equals("")) {
                        Toast.makeText(ThemSinhVienActivity.this, "Tên sinh viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (ten.matches((".*[0-9].*"))) {
                        Toast.makeText(ThemSinhVienActivity.this, "Tên chỉ được nhập chuỗi", Toast.LENGTH_LONG).show();
                    } else if (email.equals("")) {
                        Toast.makeText(ThemSinhVienActivity.this, "Email sinh viên không được để trống", Toast.LENGTH_LONG).show();
                    } else if (email.matches(pattern) == false) {
                        Toast.makeText(ThemSinhVienActivity.this, "Bạn nhập sai định dạng email", Toast.LENGTH_SHORT).show();
                    } else if (hinh.equals("")) {
                        edtHinh.setText("avatamacdinh");
                    } else {
                        SinhVien s = new SinhVien(ma, ten, email, hinh, maLop);
                        if (daoSach.insert(s)) {
                            Toast.makeText(ThemSinhVienActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ThemSinhVienActivity.this, " Không được trùng mã sinh viên ", Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ThemSinhVienActivity.this, "Lỗi : " + e, Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}
